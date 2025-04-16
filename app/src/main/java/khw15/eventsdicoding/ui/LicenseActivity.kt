package khw15.eventsdicoding.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mikepenz.aboutlibraries.LibsBuilder
import khw15.eventsdicoding.R

@Deprecated("LibsBuilder is deprecated", ReplaceWith("Compose-based UI"))
@Suppress("DEPRECATION")
class LicenseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LibsBuilder()
            .withActivityTitle(getString(R.string.oss_licenses))
            .withAboutIconShown(true)
            .withAboutVersionShownName(true)
            .withAboutVersionShownCode(true)
            .withAboutAppName(getString(R.string.app_name))
            .withSearchEnabled(true)
            .start(this)
        finish()
    }
}
