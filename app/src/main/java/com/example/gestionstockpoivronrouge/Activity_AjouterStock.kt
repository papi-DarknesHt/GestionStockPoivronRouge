package com.example.gestionstockpoivronrouge

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.gestionstockpoivronrouge.database.AppDatabase
import com.example.gestionstockpoivronrouge.repository.ProduitRepository
import com.example.gestionstockpoivronrouge.repository.StockRepository
import com.example.gestionstockpoivronrouge.viewmodel.ProduitViewModel
import com.example.gestionstockpoivronrouge.viewmodel.StockViewModel

class Activity_AjouterStock: AppCompatActivity() {
    private val stockViewModel : StockViewModel by viewModels{
        val daoStock = AppDatabase.getDatabase(this).stockDao()
        val repository = StockRepository(daoStock)
        StockViewModel.FactoryStock(repository)

    }
    private val produitViewModel: ProduitViewModel by viewModels {
        val dao = AppDatabase.getDatabase(this).produitDao()
        val repository = ProduitRepository(dao)
        ProduitViewModel.FactoryProduit(repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ajout_stock_activity)

        val spinnerProduitStock = findViewById<Spinner>(R.id.spinnerProduitStock)

        // Observer les produits et remplir le Spinner
        produitViewModel.getAllProducts().observe(this) { produits ->
            val productList = produits.map { "${it.code} | ${it.nom}" }
            val productIds = produits.map { it.id } // Liste des IDs des produits

            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, productList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerProduitStock.adapter = adapter



            spinnerProduitStock.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedProductId = productIds[position] // Récupérer l'ID du produit sélectionné
                    Toast.makeText(this@Activity_AjouterStock, "ID Produit sélectionné : $selectedProductId", Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Rien à faire ici
                }
            }
        }
    }



}

