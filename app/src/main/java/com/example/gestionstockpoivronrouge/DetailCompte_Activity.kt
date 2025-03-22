package com.example.gestionstockpoivronrouge

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailCompte_Activity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idDetail = findViewById<TextView>(R.id.dCompteId)
        val id = intent.getStringExtra("id")
        idDetail.text = id
    }
}