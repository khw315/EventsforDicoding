package khw15.eventsdicoding.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import khw15.eventsdicoding.data.local.entity.EventEntity

@Dao
interface EventDao {

    // --- Query All Events by Type ---

    @Query("SELECT * FROM EventForDicoding WHERE isUpcoming = 1 ORDER BY beginTime ASC")
    fun getUpcomingEvents(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM EventForDicoding WHERE isFinished = 1 ORDER BY endTime DESC")
    fun getFinishedEvents(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM EventForDicoding WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavoriteEvents(): LiveData<List<EventEntity>>

    // --- Search Events by Type ---

    @Query("""
        SELECT * FROM EventForDicoding 
        WHERE isUpcoming = 1 AND (
            name LIKE '%' || :query || '%' OR 
            summary LIKE '%' || :query || '%' OR 
            description LIKE '%' || :query || '%' OR 
            category LIKE '%' || :query || '%' OR 
            ownerName LIKE '%' || :query || '%'
        )
    """)
    fun searchUpcomingEvents(query: String): LiveData<List<EventEntity>>

    @Query("""
        SELECT * FROM EventForDicoding 
        WHERE isFinished = 1 AND (
            name LIKE '%' || :query || '%' OR 
            summary LIKE '%' || :query || '%' OR 
            description LIKE '%' || :query || '%' OR 
            category LIKE '%' || :query || '%' OR 
            ownerName LIKE '%' || :query || '%'
        )
    """)
    fun searchFinishedEvents(query: String): LiveData<List<EventEntity>>

    @Query("""
        SELECT * FROM EventForDicoding 
        WHERE isFavorite = 1 AND (
            name LIKE '%' || :query || '%' OR 
            summary LIKE '%' || :query || '%' OR 
            description LIKE '%' || :query || '%' OR 
            category LIKE '%' || :query || '%' OR 
            ownerName LIKE '%' || :query || '%'
        )
    """)
    fun searchFavoriteEvents(query: String): LiveData<List<EventEntity>>

    // --- Insert / Update / Delete ---

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>?)

    @Update
    suspend fun updateEvent(event: EventEntity)

    @Query("DELETE FROM EventForDicoding WHERE isFavorite = 0")
    suspend fun deleteAll()

    // --- Favorite State Check ---

    @Query("SELECT EXISTS(SELECT 1 FROM EventForDicoding WHERE name = :name AND isFavorite = 1)")
    suspend fun isEventFavorite(name: String): Boolean
}