package com.example.gestionstockpoivronrouge

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.semantics.text
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.text.toIntOrNull

class DetailCompte_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail_compte)
        val idDetail = findViewById<TextView>(R.id.dCompteId)
        val nomDetail = findViewById<TextView>(R.id.DCompteNom)
        val prenomDetail = findViewById<TextView>(R.id.DComptePrenom)
        val emailDetail = findViewById<TextView>(R.id.DCompteEmail)
        val statutDetail = findViewById<TextView>(R.id.DCompteStatut)
        val imageDetail = findViewById<ImageView>(R.id.DCompteImg)
        Log.d("DetailCompte", "idDetail est null : ${idDetail == null}")
        if(idDetail == null){
            return
        }
        val id = intent.getStringExtra("id")
        val nom = intent.getStringExtra("nom")
        val prenom = intent.getStringExtra("prenom")
        val email = intent.getStringExtra("email")
        val statut = intent.getStringExtra("statut")
        val image = intent.getIntExtra("image",0)
//        Log.d("DetailCompte", "id est null : ${id == null}")
//        Log.d("DetailCompte", "Valeur de id : $id")
//
//        if (id != null) {
//            if(id.isNotEmpty()){
//                idDetail.text = id
//            }else{
//                Log.d("DetailCompte", "id est vide")
//            }
//        }else{
//            Log.d("DetailCompte", "id est null")
//        }
        idDetail.text = id
        nomDetail.text = nom
        prenomDetail.text = prenom
        emailDetail.text = email
        statutDetail.text = statut
        imageDetail.setImageResource(image)

    }
}