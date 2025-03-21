package com.example.gestionstockpoivronrouge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button

class Gestion_activity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestioncompte)
        val intentAjoutCompte = Intent(this, ajoutCompte_Activity::class.java)
        val btAjoutCompte : Button? = findViewById(R.id.addEmp)
        if (btAjoutCompte != null) {
            btAjoutCompte.setOnClickListener(View.OnClickListener {
                startActivity(intentAjoutCompte)
            })
        }
    }
}