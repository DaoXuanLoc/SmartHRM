package com.ominext.smarthrm.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ominext.smarthrm.R
import com.ominext.smarthrm.presenter.LoginPresenter
import com.ominext.smarthrm.view.ILogin

class MainActvity : AppCompatActivity(), ILogin {

    lateinit var edtEmail: EditText
    lateinit var btLogin: Button

    private var presenter: LoginPresenter? = null
    var imei: String = ""

    companion object {
        private const val MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0
        private const val KEY_IMEI = "KEY_IMEI"
        private const val KEY_TOKEN = "KEY_TOKEN"
        private const val KEY_EMAIL = "KEY_EMAIL"

        const val TAG = "TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = LoginPresenter(this)
        initView()
    }

    private fun initView() {
        getUniqueIMEIId(this)
        edtEmail = findViewById(R.id.edt_email)
        btLogin = findViewById(R.id.btn_login)

        onclickLogin()
    }

    private fun onclickLogin() {
        btLogin.setOnClickListener {
            val email = edtEmail.text
            if (email.isNotEmpty()) {
                presenter?.login(email.toString(), "123456")
            } else {
                Toast.makeText(this, "Ban can nhap Email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("HardwareIds")
    private fun getUniqueIMEIId(context: Context): String? {
        try {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (ActivityCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestReadPhoneStatePermission()
            }
            imei = telephonyManager.deviceId
            Log.e("imei", "=$imei")
            return if (imei.isNotEmpty()) {
                imei
            } else {
                Build.SERIAL
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "not_found"
    }

    private fun requestReadPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.READ_PHONE_STATE
            )
        ) {
            AlertDialog.Builder(this)
                .setTitle("Permission Request")
                .setMessage(getString(R.string.permissions_not_granted_read_phone_state))
                .setCancelable(false)
                .setPositiveButton(
                    android.R.string.yes
                ) { _, _ ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.READ_PHONE_STATE),
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE
                    )
                }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.READ_PHONE_STATE),
                MY_PERMISSIONS_REQUEST_READ_PHONE_STATE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initView()
            } else {
                alertAlert(getString(R.string.permissions_not_granted_read_phone_state))
            }
        }
    }

    private fun alertAlert(msg: String) {
        AlertDialog.Builder(this)
            .setTitle("Permission Request")
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton(
                android.R.string.yes,
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    override fun onLoading() {
    }

    override fun onHideLoading() {
    }

    override fun onLoginSuccess(token: String?) {
        saveData(token)
        val intent = Intent()
        intent.setClass(this, ScanQRCodeActivity::class.java)
        startActivity(intent)
    }

    private fun saveData(token: String?) {
        val sharedPref: SharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(KEY_IMEI, imei)
        editor.putString(KEY_TOKEN, token)
        editor.putString(KEY_EMAIL, edtEmail.text.toString())
        editor.apply()
    }

    override fun onLoginFail() {
        Toast.makeText(this, "Tai khoan chua duoc dang ky", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDetach()
    }
}
