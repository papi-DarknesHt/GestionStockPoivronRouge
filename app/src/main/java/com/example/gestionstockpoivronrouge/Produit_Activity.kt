package com.example.gestionstockpoivronrouge

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionstockpoivronrouge.database.AppDatabase
import com.example.gestionstockpoivronrouge.repository.ProduitRepository
import com.example.gestionstockpoivronrouge.viewmodel.ProduitViewModel

class Produit_Activity : AppCompatActivity() {

    private lateinit var gestionProduitAdapter: ProduitAdapter
    private val produitViewModel: ProduitViewModel by viewModels {
        val daoProduit = AppDatabase.getDatabase(this).produitDao()
        val repository = ProduitRepository(daoProduit)
        ProduitViewModel.FactoryProduit(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityproduit)
        afficherListeProduit()
    }

    private fun afficherListeProduit() {
        val recyclerView = findViewById<RecyclerView>(R.id.listViewProduit)

        gestionProduitAdapter = ProduitAdapter(
            this,
            mutableListOf(),
            onEditClick = { produit ->
                // Logique pour modifier le produit
                val intent = Intent(this, activity_ajout_produit::class.java)
                intent.putExtra("produit", produit) // L'objet Produit est passé à l'autre activité
                startActivity(intent)
            },
            onDeleteClick = { produit ->

            },
            produitViewModel = produitViewModel
        )

        recyclerView.adapter = gestionProduitAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observer les produits depuis le ViewModel
        produitViewModel.allProduit.observe(this, Observer { produits ->
            produits?.let {
                gestionProduitAdapter.setProduit(it)  // Met à jour la liste des produits dans l'adaptateur
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
