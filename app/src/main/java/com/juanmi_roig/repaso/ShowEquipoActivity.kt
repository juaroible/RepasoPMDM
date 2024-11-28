package com.juanmi_roig.repaso

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class ShowEquipoActivity : AppCompatActivity() {

    companion object {
        const val NOMBRE = "com.juanmi_roig.repaso.ShowEquipoActivity.NOMBRE"
        const val PUNTOS = "com.juanmi_roig.repaso.ShowEquipoActivity.PUNTOS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_equipo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nombre = intent.getStringExtra(NOMBRE)
        val imageView = findViewById<ImageView>(R.id.escudo)

        val escudoUrl = "https://github.com/rafapuig/PMDM7N_2024/blob/master/escudos/$nombre.png?raw=true"
        Glide.with(imageView).load(escudoUrl).into(imageView)
    }

    override fun finish() {
        val intent = Intent()
        intent.putExtra(PUNTOS, 100)
        this.setResult(RESULT_OK, intent)
        super.finish()
    }

}