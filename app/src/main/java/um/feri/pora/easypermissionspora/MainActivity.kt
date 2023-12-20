package um.feri.pora.easypermissionspora

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import com.vmadalin.easypermissions.models.PermissionRequest
import um.feri.pora.easypermissionspora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    companion object{
        const val PERMISSION_DIFFERENT_REQUESTS_CODE = 1
    }


    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bPermissions.visibility = View.VISIBLE

        binding.bPermissions.setOnClickListener {
            requestDifferentPermissions()
        }
    }

    //tukaj prekrijemo 2 metodi, ki sta del knjižnice/vmesnika
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            SettingsDialog.Builder(this).build().show()
        }
        else{
            requestDifferentPermissions()
        }
    }

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
            binding.bPermissions.visibility = View.INVISIBLE
        }
        else{
            binding.tvText.setText("Ne morete uporabljati aplikacije")
            binding.bPermissions.visibility = View.VISIBLE
            //requestDifferentPermissions()
        }
    }

    //vrne true ali false, preverimo lahko za poljubno število dovoljenj naenkrat

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
    @AfterPermissionGranted(PERMISSION_DIFFERENT_REQUESTS_CODE) //lahko ni pa obvezno
    fun hasDifferentPermissions() =
        EasyPermissions.hasPermissions(
            this,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    //tukaj prikažemo uporabniku dovoljenja
    private fun requestDifferentPermissions() {
        EasyPermissions.requestPermissions(
            this,
            "Ta aplikacija potrebuje imeti vsa navedena dovoljenja za pravilno delovanje, Prosim, če jih potrdite.",
            PERMISSION_DIFFERENT_REQUESTS_CODE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }


}