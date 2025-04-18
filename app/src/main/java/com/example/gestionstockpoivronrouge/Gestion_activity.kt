package com.example.gestionstockpoivronrouge

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionstockpoivronrouge.database.AppDatabase
import com.example.gestionstockpoivronrouge.model.Compte
import com.example.gestionstockpoivronrouge.repository.CompteRepository
import com.example.gestionstockpoivronrouge.viewmodel.CompteViewModel

class Gestion_activity : AppCompatActivity() {

    private lateinit var compteListeAdapter: GestionAdapter
    private val compteViewModel: CompteViewModel by viewModels {
        val daoCompte = AppDatabase.getDatabase(this).compteDao()
        val repository = CompteRepository(daoCompte)
        CompteViewModel.Factory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestioncompte)
        afficherListe()
    }

    private fun afficherListe() {
        compteListeAdapter = GestionAdapter(
            this,
            mutableListOf(),
            onEditClick = { compte ->
                // Logique pour modifier le compte
                val intent = Intent(this, ajoutCompte_Activity::class.java) // Ou votre activité d'édition
                intent.putExtra("compte", compte as Parcelable) // Correct
                // Passez l'objet Compte entier
                startActivity(intent)
            },
            onDeleteClick = { compte ->
                // Logique pour supprimer le compte
                /* compteViewModel.delete(compte) // Supposons que vous avez une méthode pour supprimer un compte
                 Toast.makeText(this, "Compte ${compte.nom} supprimé", Toast.LENGTH_SHORT).show()*/
            },
            compteViewModel = compteViewModel
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCompte)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = compteListeAdapter

        // Observer les comptes depuis le ViewModel
        compteViewModel.allComptes.observe(this, Observer { comptes ->
            comptes?.let {
                compteListeAdapter.setComptes(it)
            }
        })
    }

    // Option menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.comptemenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ajouterCompte -> {
                val intentAjoutCompte = Intent(this, ajoutCompte_Activity::class.java)
                startActivity(intentAjoutCompte)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
