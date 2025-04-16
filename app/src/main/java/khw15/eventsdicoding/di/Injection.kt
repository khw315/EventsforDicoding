package khw15.eventsdicoding.di

import android.content.Context
import khw15.eventsdicoding.data.local.room.EventDatabase
import khw15.eventsdicoding.data.remote.EventRepository
import khw15.eventsdicoding.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventDatabase.getInstance(context)
        val eventDao = database.eventDao()
        return EventRepository.getInstance(apiService, eventDao)
    }
}