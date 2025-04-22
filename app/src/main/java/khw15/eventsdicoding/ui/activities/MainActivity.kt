package khw15.eventsdicoding.ui.activities

import android.Manifest
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import khw15.eventsdicoding.R
import khw15.eventsdicoding.databinding.ActivityMainBinding
import khw15.eventsdicoding.ui.dialogs.NetworkDialog
import khw15.eventsdicoding.ui.viewmodels.MainViewModel
import khw15.eventsdicoding.ui.viewmodels.ViewModelFactory
import khw15.eventsdicoding.utils.NetworkUtil.isNetworkAvailable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var networkDialog: NetworkDialog? = null
    private var dataRefreshListener: NetworkChangeListener? = null
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkRequest: NetworkRequest

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        val message = if (isGranted)
            getString(R.string.notifications_permission_granted)
        else
            getString(R.string.notifications_permission_rejected)

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            runOnUiThread {
                networkDialog?.dismissDialog()
                dataRefreshListener?.onNetworkChanged()
            }
        }

        override fun onLost(network: Network) {
            runOnUiThread {
                networkDialog?.showNoInternetDialog {
                    if (isNetworkAvailable(this@MainActivity)) {
                        networkDialog?.dismissDialog()
                        dataRefreshListener?.onNetworkChanged()
                    } else {
                        Toast.makeText(this@MainActivity, getString(R.string.offline_state), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        viewModel.getThemeSettings().observe(this@MainActivity) {
            applyDarkMode(it)
        }

        if (Build.VERSION.SDK_INT >= 33) {
            val permissionCheck = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            )
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()

        connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        networkDialog = NetworkDialog(this)
        registerNetworkCallback()
    }

    private fun setupBottomNavigation() {
        val bottomNav: BottomNavigationView = binding.bottomNav
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)
    }

    private fun applyDarkMode(isDarkMode: Boolean) {
        val mode = if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
        else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    private fun registerNetworkCallback() {
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterNetworkCallback(networkCallback)
        networkDialog?.dismissDialog()
    }

    interface NetworkChangeListener {
        fun onNetworkChanged()
    }
}