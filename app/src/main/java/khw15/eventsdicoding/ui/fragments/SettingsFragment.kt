package khw15.eventsdicoding.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import khw15.eventsdicoding.BuildConfig
import khw15.eventsdicoding.R
import khw15.eventsdicoding.ui.LicenseActivity
import khw15.eventsdicoding.ui.NotificationsWorker
import khw15.eventsdicoding.ui.viewmodels.MainViewModel
import khw15.eventsdicoding.ui.viewmodels.ViewModelFactory
import khw15.eventsdicoding.utils.SettingsKeys
import java.util.concurrent.TimeUnit

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var workManager: WorkManager

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workManager = WorkManager.getInstance(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findPreference<Preference>(SettingsKeys.APP_VERSION)?.apply {
            summary = try {
                requireContext().packageManager
                    .getPackageInfo(requireContext().packageName, 0).versionName
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    Log.e("SettingsFragment", "Error getting app version", e)
                }
                getString(R.string.unknown_version)
            }
        }

        findPreference<SwitchPreference>(SettingsKeys.THEME)?.let { themeSwitch ->
            observeThemeSetting(themeSwitch)
            themeSwitch.setOnPreferenceChangeListener { _, newValue ->
                val isDarkMode = newValue as Boolean
                viewModel.saveThemeSetting(isDarkMode)
                applyDarkMode(isDarkMode)
                true
            }
        }

        findPreference<SwitchPreference>(SettingsKeys.NOTIFICATION)?.let { notificationSwitch ->
            observeNotificationSetting(notificationSwitch)
            notificationSwitch.setOnPreferenceChangeListener { _, newValue ->
                val isReminderEnabled = newValue as Boolean
                viewModel.saveNotificationSetting(isReminderEnabled)
                applyDailyReminder(isReminderEnabled)
                true
            }
        }

        findPreference<Preference>(SettingsKeys.OSS_LICENSE)?.setOnPreferenceClickListener {
            startActivity(Intent(requireContext(), LicenseActivity::class.java))
            true
        }
    }

    private fun observeThemeSetting(switch: SwitchPreference) {
        viewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkMode ->
            switch.isChecked = isDarkMode
        }
    }

    private fun observeNotificationSetting(switch: SwitchPreference) {
        viewModel.getNotificationSettings().observe(viewLifecycleOwner) { isEnabled ->
            switch.isChecked = isEnabled
        }
    }

    private fun applyDarkMode(isDarkMode: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun applyDailyReminder(isEnabled: Boolean) {
        if (isEnabled) {
            startPeriodicNotificationWork()
        } else {
            workManager.cancelUniqueWork(SettingsKeys.NOTIFICATION_WORK_NAME)
        }
    }

    private fun startPeriodicNotificationWork() {
        val data = workDataOf(
            NotificationsWorker.EXTRA_EVENT to getString(R.string.notifications_test)
        )

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<NotificationsWorker>(24, TimeUnit.HOURS)
            .setInputData(data)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            SettingsKeys.NOTIFICATION_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )

        workManager.getWorkInfosForUniqueWorkLiveData(SettingsKeys.NOTIFICATION_WORK_NAME)
            .observe(requireActivity()) { workInfos ->
                workInfos.forEach {
                    Log.d("SettingsFragment", "Work state: ${it.state}")
                }
            }
    }
}