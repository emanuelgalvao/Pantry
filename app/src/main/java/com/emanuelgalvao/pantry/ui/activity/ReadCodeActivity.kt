package com.emanuelgalvao.pantry.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.databinding.ActivityReadCodeBinding
import com.emanuelgalvao.pantry.viewmodel.ConfigurationViewModel
import com.google.zxing.BarcodeFormat

class ReadCodeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityReadCodeBinding
    private lateinit var mViewModel: ConfigurationViewModel
    private lateinit var codeScanner: CodeScanner

    private val REQUEST_CODE_CAMERA = 100
    private var enableFlash = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mViewModel = ViewModelProvider(this).get(ConfigurationViewModel::class.java)

        mViewModel.getConfiguration()

        binding.textFillManually.setOnClickListener(this)

        observers()
    }

    override fun onPause() {
        super.onPause()
        if (codeScanner != null) {
            codeScanner.releaseResources()
        }
    }

    private fun observers() {
        mViewModel.configuration.observe(this, {
            enableFlash = it.enableFlash
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, Array(1){Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA)
            else
                initializeBarCodeReader()
        })
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.text_fill_manually) {
            startActivity(Intent(this, PantryFormActivity::class.java))
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_CAMERA -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Acesso a câmera não concedido. Preencha os dados manualmente!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, PantryFormActivity::class.java))
                    finish()
                } else {
                    initializeBarCodeReader()
                }
            }
        }
    }

    private fun initializeBarCodeReader() {

        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        codeScanner = CodeScanner(this, scannerView)

        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = listOf(BarcodeFormat.EAN_13)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = enableFlash

        codeScanner.decodeCallback = DecodeCallback {
            val bundle = Bundle()
            bundle.putString("barCode", it.text)
            val intent = Intent(this, PantryFormActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }
        codeScanner.errorCallback = ErrorCallback {
            Log.e("message: ", it.message.toString())
            finish()
        }

        codeScanner.startPreview()
    }
}