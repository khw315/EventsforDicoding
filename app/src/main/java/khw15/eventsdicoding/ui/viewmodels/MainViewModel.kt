package khw15.eventsdicoding.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import khw15.eventsdicoding.data.local.entity.EventEntity
import khw15.eventsdicoding.data.remote.EventRepository
import khw15.eventsdicoding.ui.SettingPreferences
import khw15.eventsdicoding.utils.Result
import kotlinx.coroutines.launch

class MainViewModel(
    private val eventRepository: EventRepository,
    private val settingPreferences: SettingPreferences
) : ViewModel() {

    fun getUpcomingEvents() = eventRepository.getEvents(active = 1)
    fun getFinishedEvents() = eventRepository.getEvents(active = 0)
    fun getFavoriteEvents() = eventRepository.getFavoriteEvents()

    fun searchUpcomingEvents(query: String): LiveData<Result<List<EventEntity>>> =
        eventRepository.searchEvents(1, query)

    fun searchFinishedEvents(query: String): LiveData<Result<List<EventEntity>>> =
        eventRepository.searchEvents(0, query)

    fun searchFavoriteEvents(query: String): LiveData<Result<List<EventEntity>>> =
        eventRepository.searchEvents(99, query)

    fun saveEvents(events: EventEntity) {
        viewModelScope.launch {
            eventRepository.setFavoriteEvents(events, true)
        }
    }

    fun deleteEvents(events: EventEntity) {
        viewModelScope.launch {
            eventRepository.setFavoriteEvents(events, false)
        }
    }

    // Theme functions
    fun getThemeSettings() = settingPreferences.getThemeSetting().asLiveData()
    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            settingPreferences.saveThemeSetting(isDarkModeActive)
        }
    }

    // Notification functions
    fun getNotificationSettings() = settingPreferences.getNotificationSetting().asLiveData()
    fun saveNotificationSetting(isNotificationActive: Boolean) {
        viewModelScope.launch {
            settingPreferences.saveNotificationSetting(isNotificationActive)
        }
    }


}