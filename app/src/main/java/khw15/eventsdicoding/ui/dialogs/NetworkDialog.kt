package khw15.eventsdicoding.ui.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import khw15.eventsdicoding.R

class NetworkDialog(private val context: Context) {
    private var alertDialog: AlertDialog? = null

    @SuppressLint("InflateParams")
    fun showNoInternetDialog(retryAction: () -> Unit) {
        if (alertDialog?.isShowing == true) return

        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_no_internet, null)

        val retryButton = dialogView.findViewById<Button>(R.id.btn_retry)
        val closeButton = dialogView.findViewById<ImageView>(R.id.btn_close)

        alertDialog = AlertDialog.Builder(context).apply {
            setView(dialogView)
            setCancelable(false)
        }.create().also {
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
            it.show()
        }

        retryButton.setOnClickListener {
            retryAction()
        }

        closeButton.setOnClickListener {
            dismissDialog()
        }
    }

    fun dismissDialog() {
        if (alertDialog?.isShowing == true) {
            alertDialog?.dismiss()
        }
    }
}