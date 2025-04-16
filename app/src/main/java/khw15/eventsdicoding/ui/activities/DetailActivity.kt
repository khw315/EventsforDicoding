package khw15.eventsdicoding.ui.activities

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import khw15.eventsdicoding.R
import khw15.eventsdicoding.data.local.entity.EventEntity
import khw15.eventsdicoding.databinding.ActivityDetailBinding
import khw15.eventsdicoding.ui.viewmodels.MainViewModel
import khw15.eventsdicoding.ui.viewmodels.ViewModelFactory
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val customTabsIntent by lazy { CustomTabsIntent.Builder().build() }
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val event: EventEntity? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_EVENT, EventEntity::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_EVENT)
        }

        if (event == null) {
            finish() // Gracefully exit if no event is passed
            return
        }

        setupEventDetails(event)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun formatEventDate(dateString: String?): String? {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val date = inputFormat.parse(dateString ?: "") ?: return null

            val outputFormat = DateFormat.getDateTimeInstance(
                DateFormat.LONG,
                DateFormat.SHORT,
                Locale.getDefault()
            )

            outputFormat.format(date)
        } catch (e: Exception) {
            Log.e("DateFormatError", "Error formatting date", e)
            Toast.makeText(this, "${e.printStackTrace()}", Toast.LENGTH_SHORT).show()
            null
        }
    }

    private fun setupEventDetails(event: EventEntity) {
        binding.apply {
            val remainingQuota = (event.quota ?: 0) - (event.registrants ?: 0)
            val isQuotaFull = remainingQuota <= 0

            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val eventDate = inputFormat.parse(event.beginTime ?: "")
            val isEventStarted = eventDate?.before(java.util.Date()) == true

            updateFavoriteIcon(event.isFavorite)

            titleTextView.text = event.name
            ownerTextView.text = getString(R.string.organized_by, event.ownerName)
            remainingQuotaTextView.text = if (isQuotaFull || isEventStarted) {
                getString(R.string.registration_closed)
            } else {
                getString(R.string.remaining_quota, remainingQuota.toString())
            }
            eventCityTextView.text = event.cityName
            beginTimeTextView.text = formatEventDate(event.beginTime)
            endTimeTextView.text = formatEventDate(event.endTime)
            summaryTextView.text = event.summary
            descriptionTextView.apply {
                text = Html.fromHtml(event.description, Html.FROM_HTML_MODE_COMPACT)
                movementMethod = LinkMovementMethod.getInstance()
            }

            Glide.with(this@DetailActivity)
                .load(event.mediaCover)
                .into(imageView)

            favoriteFab.setOnClickListener {
                toggleFavorite(event)
            }

            webpageFab.setOnClickListener {
                openWebPage(event.link)
            }
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean?) {
        binding.favoriteFab.setImageResource(
            if (isFavorite == true) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        )
    }

    private fun toggleFavorite(event: EventEntity) {
        event.isFavorite = event.isFavorite != true

        binding.favoriteFab.animate()
            .setDuration(300)
            .scaleX(1.2f)
            .scaleY(1.2f)
            .withEndAction {
                binding.favoriteFab.animate()
                    .setDuration(300)
                    .scaleX(1f)
                    .scaleY(1f)
                    .start()
            }

        updateFavoriteIcon(event.isFavorite)
        if (event.isFavorite == true) {
            viewModel.saveEvents(event)
        } else {
            viewModel.deleteEvents(event)
        }
    }

    private fun openWebPage(url: String?) {
        url?.let {
            customTabsIntent.launchUrl(this, it.toUri())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_EVENT = "EXTRA_EVENT"
    }
}
