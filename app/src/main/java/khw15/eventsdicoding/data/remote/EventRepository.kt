package khw15.eventsdicoding.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import khw15.eventsdicoding.data.local.entity.EventEntity
import khw15.eventsdicoding.data.local.room.EventDao
import khw15.eventsdicoding.data.remote.response.EventResponse
import khw15.eventsdicoding.data.remote.retrofit.ApiService
import khw15.eventsdicoding.utils.Result

class EventRepository(
    private val apiService: ApiService,
    private val eventDao: EventDao,
) {

    fun getEvents(active: Int): LiveData<Result<List<EventEntity>>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.getEvents(active = active)
            val eventList = mutableListOf<EventEntity>()

            response.listEvents?.let { events ->
                for (event in events) {
                    val isFavorite = event.name?.let { eventDao.isEventFavorite(it) } ?: false
                    val entity = EventEntity(
                        id = event.id,
                        name = event.name,
                        summary = event.summary,
                        description = event.description,
                        imageLogo = event.imageLogo,
                        mediaCover = event.mediaCover,
                        category = event.category,
                        ownerName = event.ownerName,
                        cityName = event.cityName,
                        quota = event.quota,
                        registrants = event.registrants,
                        beginTime = event.beginTime,
                        endTime = event.endTime,
                        link = event.link,
                        isFavorite = isFavorite,
                        isUpcoming = active == 1,
                        isFinished = active == 0,
                    )
                    eventList.add(entity)
                }
            }

            eventDao.insertEvents(eventList)
        } catch (e: Exception) {
            Log.e("EventRepository", "getEvents: ${e.message}")
            emit(Result.Error(e.message ?: "Unknown error"))
        }

        // Emit local database data
        val localData = when (active) {
            1 -> eventDao.getUpcomingEvents()
            else -> eventDao.getFinishedEvents()
        }.mapToResult()

        emitSource(localData)
    }

    fun getFavoriteEvents(): LiveData<List<EventEntity>> =
        eventDao.getFavoriteEvents()

    suspend fun setFavoriteEvents(events: EventEntity, favorite: Boolean) {
        events.isFavorite = favorite
        eventDao.updateEvent(events)
    }

    fun searchEvents(active: Int, query: String): LiveData<Result<List<EventEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val localData = when (active) {
                1 -> eventDao.searchUpcomingEvents(query)
                0 -> eventDao.searchFinishedEvents(query)
                else -> eventDao.searchFavoriteEvents(query)
            }.mapToResult()

            emitSource(localData)
        } catch (e: Exception) {
            Log.e("EventRepository", "searchEvents: ${e.message}")
            emit(Result.Error(e.message ?: "Unknown error"))
        }
    }

    suspend fun getLatestEvent(): EventResponse? = try {
        apiService.getEvents(active = -1, limit = 1)
    } catch (e: Exception) {
        Log.e("EventRepository", "Error fetching latest event: ${e.message}")
        null
    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null

        fun getInstance(
            apiService: ApiService,
            eventDao: EventDao,
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, eventDao).also {
                    instance = it
                }
            }
    }

    // --- Extension Helper ---

    private fun LiveData<List<EventEntity>>.mapToResult(): LiveData<Result<List<EventEntity>>> =
        this.map { Result.Success(it) }
}
