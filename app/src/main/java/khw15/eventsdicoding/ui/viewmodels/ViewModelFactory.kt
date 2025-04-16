package khw15.eventsdicoding.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import khw15.eventsdicoding.data.remote.EventRepository
import khw15.eventsdicoding.di.Injection
import khw15.eventsdicoding.ui.SettingPreferences
import khw15.eventsdicoding.ui.dataStore

class ViewModelFactory(
    private val eventRepository: EventRepository,
    private val settingPreferences: SettingPreferences
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(eventRepository, settingPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                val repository = Injection.provideRepository(context)
                val preferences = SettingPreferences.getInstance(context.dataStore)
                instance ?: ViewModelFactory(repository, preferences)
            }.also { instance = it }
    }
}