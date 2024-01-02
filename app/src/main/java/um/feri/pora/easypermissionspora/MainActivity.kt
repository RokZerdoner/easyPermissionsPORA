package um.feri.pora.easypermissionspora

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AlertDialog
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import com.vmadalin.easypermissions.dialogs.DEFAULT_SETTINGS_REQ_CODE
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import com.vmadalin.easypermissions.models.PermissionRequest
import um.feri.pora.easypermissionspora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    companion object{
        const val PERMISSION_DIFFERENT_REQUESTS_CODE = 1
        const val SETTINGS_REQ_CODE = 2
    }


    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bPermissions.visibility = View.VISIBLE

        binding.bPermissions.setOnClickListener {
            //requestDifferentPermissions()

            val request = buildPermissionRequest()
            EasyPermissions.requestPermissions(this, request)
        }
    }

    //tukaj prekrijemo 2 metodi, ki sta del knjižnice/vmesnika
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)){ // samo, če so nekatera dovoljenja za vedno prepovedana
            //SettingsDialog.Builder(this).build().show()

            showCustomSettingsDialog()
        }
        else{
            //če katero zavrne lahko tudi tukaj podamo, da se ponovno prikažejo obvestila


            //requestDifferentPermissions()

            //val request = buildPermissionRequest()
            //EasyPermissions.requestPermissions(this, request)
        }
    }

    //to je potrebno prekriti, da se sploh prikažejo dovoljenja
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    //kaj se zgodi, ko so dovoljenja pridobljena
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        //EasyPermissions.requestPermissions(this, request)
        if(hasDifferentPermissions()) {
            Toast.makeText(this, "Vsa dovoljenja so pridobljena", Toast.LENGTH_SHORT).show()
            binding.tvText.setText("Lahko uporabljate aplikacijo")
            binding.tvText2.setText("Lahko uporabljate kamero")
            binding.tvText3.setText("Lahko uporabljate lokacijo/zemljevid")
            binding.bPermissions.visibility = View.INVISIBLE
        }
        else if(!hasCameraPermission() && !hasLocationPermission()){
            binding.tvText.setText("VSa dovoljenja zavrnjena")
            binding.bPermissions.visibility = View.VISIBLE

            //tukaj lahko pokličete request permission

            //requestDifferentPermissions()

            //val request = buildPermissionRequest()
            //EasyPermissions.requestPermissions(this, request)
        }
        else if(hasLocationPermission()){
            binding.tvText.setText("Niso pridobljena vsa dovoljenja")
            binding.tvText2.setText("Ne morete uporabljati kamero")
            binding.tvText3.setText("Lahko uporabljate lokacijo/zemljevid")
            binding.bPermissions.visibility = View.VISIBLE
        }
        else{
            binding.tvText.setText("Niso pridobljena vsa dovoljenja")
            binding.tvText2.setText("Lahko uporabljate kamero")
            binding.tvText3.setText("ne morete uporabljati lokacijo/zemljevid")
            binding.bPermissions.visibility = View.VISIBLE
        }
    }

    //vrne true ali false, preverimo lahko za poljubno število dovoljenj naenkrat



    @AfterPermissionGranted(PERMISSION_DIFFERENT_REQUESTS_CODE) //lahko ni pa obvezno
    fun hasDifferentPermissions() =
        EasyPermissions.hasPermissions(
            this,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )    //preverimo, če so dovoljenja dovoljena
    fun hasCameraPermission() =
        EasyPermissions.hasPermissions(
            this,
            Manifest.permission.CAMERA
        )//lahko posamično pregledamo dovoljenja
    fun hasLocationPermission() =
        EasyPermissions.hasPermissions(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    private fun requestDifferentPermissions() {
        EasyPermissions.requestPermissions(
            this,
            "Ta aplikacija potrebuje imeti vsa navedena dovoljenja za pravilno delovanje, Prosim, če jih potrdite.", //tu je lahko poljuben niz znakov -> R.string...
            PERMISSION_DIFFERENT_REQUESTS_CODE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }


    private fun buildPermissionRequest(): PermissionRequest {
        return PermissionRequest.Builder(this@MainActivity)
            .code(PERMISSION_DIFFERENT_REQUESTS_CODE)
            .perms(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            .theme(R.style.My_Theme_EasyPermissionsPORA)
            .rationale("Resnično je potrebno, da potrdite ta dovoljenja, če ne aplikacija ne more delovati")
            .positiveButtonText("Razumem")
            .negativeButtonText("Še vedno NE")
            .build()
    } // lahko tudi to:
    /*val request = PermissionRequest.Builder(this)
        .code(PERMISSION_DIFFERENT_REQUESTS_CODE)
        .perms(
            Array<String>(3) {
                Manifest.permission.CAMERA
                Manifest.permission.ACCESS_FINE_LOCATION
                Manifest.permission.ACCESS_COARSE_LOCATION
            })
        .theme(R.style.Base_Theme_EasyPermissionsPORA)
        .rationale("test")
        .positiveButtonText("ok")
        .negativeButtonText("cancel")
        .build()*/


    override fun onRationaleAccepted(requestCode: Int) {
        // Rationale accepted to request some permissions
        System.out.println("Rationale accepted for requestCode: $requestCode")
        // Additional actions or proceed with permission request
        //requestDifferentPermissions()
    }

    override fun onRationaleDenied(requestCode: Int) {
        // Rationale denied to request some permissions
        System.out.println("Rationale denied for requestCode: $requestCode")
        // Handle denial, show additional information, or take appropriate action
        //showPermissionDeniedMessage()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DEFAULT_SETTINGS_REQ_CODE) {
            val yes = "Dovoljeno"
            val no = "Zavrnjeno"

            // Check if permissions are granted after the user returned from app settings.
            if (EasyPermissions.hasPermissions(
                    this,
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                // Permissions are now granted
                Toast.makeText(
                    this,
                    getString(
                        R.string.returned_from_app_settings_to_activity,
                        yes,
                        yes
                    ),
                    LENGTH_LONG
                ).show()

                binding.tvText.setText("Lahko uporabljate aplikacijo")
                binding.tvText2.setText("Lahko uporabljate kamero")
                binding.tvText3.setText("Lahko uporabljate lokacijo/zemljevid")
                binding.bPermissions.visibility = View.INVISIBLE

                // Do additional actions if needed
            } else {
                // Permissions are still not granted
                Toast.makeText(
                    this,
                    getString(
                        R.string.returned_from_app_settings_to_activity,
                        no,
                        no
                    ),
                    LENGTH_LONG
                ).show()

                if (EasyPermissions.somePermissionPermanentlyDenied(
                        this, listOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                    && !EasyPermissions.hasPermissions(
                        this,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                ) {
                    showPermissionDeniedMessage()
                } else {
                    // Request the permissions again if needed
                    requestDifferentPermissions()

                    //val request = buildPermissionRequest()
                    //EasyPermissions.requestPermissions(this, request)
                }
            }
        }
    }
    private fun showPermissionDeniedMessage() {
        AlertDialog.Builder(this)
            .setTitle("Dovoljenja še vedno zavrnjena")
            .setMessage("VSa dovoljenja so potrebna za delovanje. Aplikacij ne more drugače pravilno delovati")
            .setPositiveButton("Razumem") { dialog, _ -> finish() }
            .show()
    }

















    fun showCustomSettingsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Dovoljena za vedno zavrnjena")
            .setMessage("Nekatera dovoljena, ki so nujno potrebna so še vedno zavrnjena. Preusmerili vas bomo na Nastavitve, da jih lahko potrdite.")
            .setPositiveButton("Velja") { dialog, which ->
                // Handle positive button click
                dialog.dismiss()

                // Redirect to app settings
                redirectToAppSettings()
            }
            .setNegativeButton("Ne") { dialog, which ->
                // Handle negative button click
                dialog.dismiss()
                finish()
            }
            .show()
    }

    private fun redirectToAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", this.packageName, null)
        intent.data = uri
        startActivityForResult(intent, DEFAULT_SETTINGS_REQ_CODE)
    }
}