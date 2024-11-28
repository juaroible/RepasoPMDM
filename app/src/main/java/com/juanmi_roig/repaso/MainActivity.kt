package com.juanmi_roig.repaso

import android.Manifest.permission.RECORD_AUDIO
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.juanmi_roig.repaso.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    /*lateinit var escudo:ImageView
    lateinit var nombreEquipo:TextView*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*val escudoView = ImageView(this)
        escudoView.setImageResource(android.R.drawable.ic_dialog_email)*/

        binding = ActivityMainBinding.inflate(layoutInflater)

        //setContentView(R.layout.activity_main)

        setContentView(binding.root)

        initViews()
        initListeners()

    }

    private fun initViews() {
        val escudoUrl = "https://github.com/rafapuig/PMDM7N_2024/blob/master/escudos/valencia.png?raw=true"
        /*escudo = findViewById<ImageView>(R.id.escudo)
        escudo.setImageResource(android.R.drawable.btn_star_big_on) */
        Glide.with(binding.escudo)
            .load(escudoUrl)
            .into(binding.escudo)

        //nombreEquipo = findViewById<TextView>(R.id.nombre_equipo)
        binding.nombreEquipo.text = "Valencia CF"
    }

    private fun initListeners() {
        binding.escudo.setOnClickListener { onEscudoClick(it) }
        binding.recordAudio.setOnClickListener { onRecordAudio(it) }
        binding.selectEquipo.setOnClickListener { onSelectEquipo(it) }
    }

    fun onSelectEquipo(view: View) {
        val intent = Intent(this, SelectEquipoActivity::class.java)
        startActivity(intent)
    }

    private fun onEscudoClick(view: View) {
        Toast.makeText(view.context, "PETER LIM SE DONDE VIVES", Toast.LENGTH_LONG)
            .show()
    }

    private fun onRecordAudio(view: View) {
        onStartRecording()
    }

    private fun isRecordingAudioPermissionGranted() =
        checkSelfPermission(RECORD_AUDIO) == PERMISSION_GRANTED

    /*private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted -> onRequestRecordPermissionResult(isGranted) }*/

    private val requestPermissionLauncher : ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { onRequestRecordPermissionResult(it) }

    private fun requestRecordPermission() {
        //requestPermissions(arrayOf(RECORD_AUDIO), 12345)
        requestPermissionLauncher.launch(RECORD_AUDIO)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==12345) {
            /*if (grantResults[0] == PERMISSION_GRANTED) {
                startRecording()
            }*/
            onRequestRecordPermissionResult(grantResults[0] == PERMISSION_GRANTED)
        }
    }

    private fun onRequestRecordPermissionResult(isGranted:Boolean) {
        if (isGranted) {
            startRecording()
        } else {
            Toast.makeText(this,"NO SE PUEDE GRABAR SIN PERMISO", Toast.LENGTH_LONG)
                .show()
        }
    }

    // Metodo principal para pedir permiso antes de grabar
    private fun onStartRecording() {
        //Si no tenemos el permiso
        if (!isRecordingAudioPermissionGranted()) {
            // Si tenemos que explicar pq es necesario el permiso
            if(shouldShowRequestPermissionRationale(RECORD_AUDIO)) {
                requestRecordPermissionRationale()
            } else { //Si no, lo pedimos directamente
                requestRecordPermission()
            }
            Toast.makeText(this, "No tienes permiso para grabar audio", Toast.LENGTH_LONG)
                .show()
        } else {
            startRecording()
        }
    }

    private fun requestRecordPermissionRationale() {
        val builder = AlertDialog.Builder(this)

        val dialog = builder.setMessage("El permiso para acceder al microgo es necesario para que la app pueda grabar audio")
            .setTitle("Permiso de grabación de audio requerido")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Aceptar"
            ) { _, _ -> requestRecordPermission() }
            .create()

        dialog.show()
    }

    private fun startRecording() {
        Toast.makeText((this), "GRABACIÓN INICIADA", Toast.LENGTH_LONG).show()
    }

}