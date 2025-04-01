package com.example.gestionstockpoivronrouge

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity

class HomeActivity: AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val btCompte : Button = findViewById(R.id.btCompt)
        produit()
        compte()
        stock()
        val intent = intent
        val statut = intent.getStringExtra("user_statut")
        if (statut != "ADMIN"){
//            btCompte?.apply {
//                isEnabled = false  // Désactiver le bouton
//                alpha = 0.5f       // Réduire l'opacité du bouton
//            }

        }
    }// fin Oncreate

    private fun stock() {
        val intentStock =Intent(this,ActivityStock::class.java)
        var btStock :Button? = findViewById(R.id.btStock)
        if(btStock != null){
            btStock.setOnClickListener(View.OnClickListener {
                startActivity(intentStock)
            })
        }
    }

    private fun compte() {
        val intentCompte = Intent(this,Gestion_activity ::class.java)
        val btCompte : Button? = findViewById(R.id.btCompt)
        btCompte?.setOnClickListener(View.OnClickListener {
            startActivity(intentCompte)
        })
    }

    private fun produit() {
        val intentProduit = Intent(this,Produit_Activity ::class.java)
        var btProduit : Button? = findViewById(R.id.btProd)
        if (btProduit != null) {
            btProduit.setOnClickListener(View.OnClickListener {
                startActivity(intentProduit)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menulogout ->{
                finish()
            }

        }

        return super.onOptionsItemSelected(item)
    }
}