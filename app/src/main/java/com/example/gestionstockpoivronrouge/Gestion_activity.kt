package com.example.gestionstockpoivronrouge

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast


class Gestion_activity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestioncompte)
        afficherListe()
    }

    private fun afficherListe() {
        val listCompte = findViewById<ListView>(R.id.listviewCompte)
        val Compte = arrayListOf(
            GestionCompte_data("5","Napoleon","Wagnerson","napoleonwagnerson@gmail.com","Admin","admin",R.drawable.logo),
            GestionCompte_data("5","Napoleon","Wagnerson","napoleonwagnerson@gmail.com","Admin","admin",R.drawable.logo),
            GestionCompte_data("5","Napoleon","Wagnerson","napoleonwagnerson@gmail.com","Admin","admin",R.drawable.logo)
        )
        val adapter = GestionAdapter(this,R.layout.item_gestion_compte,Compte)
        listCompte.adapter = adapter

        listCompte.setOnItemClickListener{ adapterView, view, position, id ->
            val clickedCompte = Compte[position]
            Intent(this, DetailCompte_Activity::class.java).also {
                it.putExtra("id",clickedCompte.id)
                startActivity(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.comptemenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.ajouterCompte ->{
                var intentAjoutCompte= Intent(this, ajoutCompte_Activity::class.java)
                startActivity(intentAjoutCompte)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}