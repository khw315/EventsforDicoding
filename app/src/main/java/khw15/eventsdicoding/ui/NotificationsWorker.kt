package khw15.eventsdicoding.ui

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BigPictureStyle
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import khw15.eventsdicoding.R
import khw15.eventsdicoding.data.remote.EventRepository
import khw15.eventsdicoding.di.Injection
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

class NotificationsWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    companion object {
        private val TAG = NotificationsWorker::class.java.simpleName
        const val EXTRA_EVENT = "EventForDicoding"
        const val NOTIFICATION_ID = 25
        const val CHANNEL_ID = "25152515"
    }

    private val repository: EventRepository = Injection.provideRepository(applicationContext)

    override suspend fun doWork(): Result = try {
        val latestEvent = repository.getLatestEvent()?.listEvents?.firstOrNull()

        if (latestEvent?.mediaCover != null && latestEvent.link != null) {
            val formattedDate = latestEvent.beginTime?.let { formatEventDate(it) }

            val title = applicationContext.getString(
                R.string.notification_title,
                formattedDate.orEmpty()
            )
            val description = applicationContext.getString(
                R.string.notification_description,
                latestEvent.name.orEmpty()
            )

            showNotification(title, description, latestEvent.mediaCover, latestEvent.link)
        }

        Result.success()
    } catch (e: Exception) {
        Log.e(TAG, applicationContext.getString(R.string.error_fetching_event, e.message))
        Result.failure()
    }

    private fun showNotification(
        title: String,
        description: String,
        imageUrl: String,
        link: String
    ) {
        val intent = Intent(Intent.ACTION_VIEW, link.toUri())
        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val notificationManager = applicationContext.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        Glide.with(applicationContext)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                @SuppressLint("ObsoleteSdkInt")
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(title)
                        .setContentText(description)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setStyle(BigPictureStyle().bigPicture(resource))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channel = NotificationChannel(
                            CHANNEL_ID,
                            applicationContext.getString(R.string.notification_channel_name),
                            NotificationManager.IMPORTANCE_HIGH
                        )
                        notificationManager.createNotificationChannel(channel)
                    }

                    notificationManager.notify(NOTIFICATION_ID, builder.build())
                }

                override fun onLoadCleared(placeholder: Drawable?) = Unit
            })
    }

    private fun formatEventDate(dateString: String): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = inputFormat.parse(dateString)

        val outputFormat = DateFormat.getDateTimeInstance(
            DateFormat.LONG,
            DateFormat.SHORT,
            Locale.getDefault()
        )

        return date?.let { outputFormat.format(it) }
    }
}
