package com.example.gestionstockpoivronrouge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.gestionstockpoivronrouge.database.AppDatabase
import com.example.gestionstockpoivronrouge.model.Compte
import com.example.gestionstockpoivronrouge.repository.CompteRepository
import com.example.gestionstockpoivronrouge.viewmodel.CompteViewModel

class Gestion_activity : AppCompatActivity() {

    private lateinit var compteListeAdapter: GestionAdapter

    // Instanciation correcte du ViewModel avec la Factory incluse dans CompteViewModel
    private val compteViewModel: CompteViewModel by viewModels {
        val daoCompte = AppDatabase.getDatabase(this).compteDao()
        val repository = CompteRepository(daoCompte)
        CompteViewModel.Factory(repository) // Utilisation correcte de la Factory interne
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestioncompte)
        afficherListe()
    }

    private fun afficherListe() {
        compteListeAdapter = GestionAdapter(this, mutableListOf())
        val listCompte = findViewById<ListView>(R.id.listviewCompte)
        listCompte.adapter = compteListeAdapter

        // Observation des comptes depuis le ViewModel
        compteViewModel.allComptes.observe(this, Observer { comptes ->
            comptes?.let {
                compteListeAdapter.setComptes(it)
            }
        })

        listCompte.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            if (position >= 0) {
                val clickedCompte = listCompte.getItemAtPosition(position) as Compte
                val intent = Intent(this, DetailCompte_Activity::class.java).apply {
                    putExtra("id", clickedCompte.id)
                    putExtra("nom", clickedCompte.nom)
                    putExtra("prenom", clickedCompte.prenom)
                    putExtra("email", clickedCompte.email)
                    putExtra("statut", clickedCompte.statut)
                }
                startActivity(intent)
            } else {
                Log.e("Gestion Compte", "Position invalide: $position")
            }
        }
    }

    // Option menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.comptemenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ajouterCompte -> {
                val intentAjoutCompte = Intent(this, ajoutCompte_Activity::class.java)
                startActivity(intentAjoutCompte)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
