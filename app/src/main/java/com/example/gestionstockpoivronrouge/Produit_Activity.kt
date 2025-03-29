package com.example.gestionstockpoivronrouge

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionstockpoivronrouge.database.AppDatabase
import com.example.gestionstockpoivronrouge.repository.ProduitRepository
import com.example.gestionstockpoivronrouge.viewmodel.ProduitViewModel
import androidx.lifecycle.Observer


class Produit_Activity : AppCompatActivity() {

    private lateinit var gestionProduit_Activity: ProduitAdapter
    private val produitViewModel: ProduitViewModel by viewModels {
        val daoProduit = AppDatabase.getDatabase(this).produitDao()
        val repository = ProduitRepository(daoProduit)
        ProduitViewModel.FactoryProduit(repository) // Utilisation correcte de la Factory interne
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityproduit)
        afficherListeProduit()

    }

    private fun afficherListeProduit() {
        val recyclerView = findViewById<RecyclerView>(R.id.listViewProduit)
        gestionProduit_Activity = ProduitAdapter(
            this,
            mutableListOf(),
            onEditClick = { produit ->
                // Logique pour modifier le compte
                /* val intent = Intent(this, EditCompteActivity::class.java)
                 intent.putExtra("compteId", compte.id)
                 startActivity(intent)*/
            },
            onDeleteClick = { produit ->
                // Logique pour supprimer le compte
                /* compteViewModel.delete(compte) // Supposons que vous avez une méthode pour supprimer un compte
                 Toast.makeText(this, "Compte ${compte.nom} supprimé", Toast.LENGTH_SHORT).show()*/
            },
            produitViewModel = produitViewModel
        )
        recyclerView.adapter = gestionProduit_Activity


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = gestionProduit_Activity

        // Observer les comptes depuis le ViewModel
        produitViewModel.allProduit.observe(this, Observer { produits ->
            produits?.let {
                gestionProduit_Activity.setProduit(it)
            }
        })

    }

    // Option menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.produit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ajouter_produit -> {
                val intentAjoutProduit = Intent(this, activity_ajout_produit::class.java)
                startActivity(intentAjoutProduit)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}