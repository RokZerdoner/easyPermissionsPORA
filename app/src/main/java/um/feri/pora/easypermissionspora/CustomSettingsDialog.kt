package um.feri.pora.easypermissionspora

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import com.vmadalin.easypermissions.dialogs.DEFAULT_SETTINGS_REQ_CODE

class CustomSettingsDialog(private val context: Context) {

    class CustomSettingsDialog(private val context: Context) {

        fun show() {
            AlertDialog.Builder(context)
                .setTitle("Custom Title")
                .setMessage("Your custom rationale goes here.")
                .setPositiveButton("Custom Positive Button") { dialog, which ->
                    // Handle positive button click
                    dialog.dismiss()
                }
                .setNegativeButton("Custom Negative Button") { dialog, which ->
                    // Handle negative button click
                    dialog.dismiss()

                    // Redirect to app settings
                    redirectToAppSettings()
                }
                .show()
        }

        private fun redirectToAppSettings() {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
        }
    }

    // Call this method from your activity/fragment's onActivityResult
}