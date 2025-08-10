package khw15.eventsdicoding

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import khw15.eventsdicoding.data.local.entity.EventEntity
import khw15.eventsdicoding.data.local.room.EventDao
import khw15.eventsdicoding.data.local.room.EventDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlin.test.assertEquals

class ExampleUnitTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: EventDatabase
    private lateinit var dao: EventDao

    @Before
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, EventDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.eventDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertFavoriteEvent_andRetrieve() = runBlocking {
        val event = EventEntity(
            id = 1,
            name = "Sample Event",
            summary = null,
            description = null,
            imageLogo = null,
            mediaCover = null,
            category = null,
            ownerName = null,
            cityName = null,
            quota = null,
            registrants = null,
            beginTime = null,
            endTime = null,
            link = null,
            isFavorite = true,
            isUpcoming = null,
            isFinished = null,
        )

        dao.insertEvents(listOf(event))
        val favorites = dao.getFavoriteEvents().getOrAwaitValue()

        assertEquals(listOf(event), favorites)
    }

    private fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }

        this.observeForever(observer)

        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}
