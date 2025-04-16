package khw15.eventsdicoding.data.remote.retrofit

import khw15.eventsdicoding.data.remote.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getEvents(
        @Query("active") active: Int = 1,  // Default is 1 for upcoming events
        @Query("q") query: String? = null, // Optional search query
        @Query("limit") limit: Int = 40   // Default limit is 40 events
    ): EventResponse
}