package com.example.gestionstockpoivronrouge

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
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
import androidx.lifecycle.lifecycleScope
import com.example.gestionstockpoivronrouge.database.AppDatabase
import com.example.gestionstockpoivronrouge.model.Stock
import com.example.gestionstockpoivronrouge.repository.ProduitRepository
import com.example.gestionstockpoivronrouge.repository.StockRepository
import com.example.gestionstockpoivronrouge.viewmodel.ProduitViewModel
import com.example.gestionstockpoivronrouge.viewmodel.StockViewModel
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class Activity_AjouterStock: AppCompatActivity() {
    private var selectedProductId by Delegates.notNull<Int>()
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
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ajout_stock_activity)
        val spinnerProduitStock = findViewById<Spinner>(R.id.spinnerProduitStock)

        val btajoutstock = findViewById<Button>(R.id.saveStock)
        val stock = intent.getParcelableExtra<Stock>("stock")
        val stockid = stock?.id ?: -1


            // Observer les produits et remplir le Spinner
            produitViewModel.getAllProducts().observe(this) { produits ->
                val productList = produits.map { "${it.code} | ${it.nom}" }
                val productIds = produits.map { it.id } // Liste des IDs des produits
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, productList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerProduitStock.adapter = adapter
                spinnerProduitStock.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            selectedProductId =
                                productIds[position] // Récupérer l'ID du produit sélectionné
                            Toast.makeText(
                                this@Activity_AjouterStock,
                                "ID Produit sélectionné : $selectedProductId",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            // Rien à faire ici
                        }
                    }
                btajoutstock.setOnClickListener {

                    val quantiteStock = findViewById<EditText>(R.id.editTextQteStock)
                    val quantiteText = quantiteStock.text.toString().trim()  // Récupère la valeur de l'EditText
                    if (quantiteText.isEmpty()) {
                        Toast.makeText(this, "Veuillez entrer une quantité", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener  // Si la quantité est vide, on arrête l'exécution
                    }
                    val intqutestock = quantiteText.toInt()

                val stock = Stock(
                    id = if (stockid != -1) stockid else 0,
                    id_produit = selectedProductId,
                    qte = intqutestock
                )
                lifecycleScope.launch {
                    if (stockid == -1) {
                        stockViewModel.ajouterStock(stock) { success, message ->
                            runOnUiThread {
                                Toast.makeText(
                                    this@Activity_AjouterStock,
                                    message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                if (success) {
                                    setResult(Activity.RESULT_OK, Intent())
                                    finish()
                                }
                            }
                        }
                    } else {
                        stockViewModel.modifierStock(stock) { success, message ->
                            runOnUiThread {
                                Toast.makeText(
                                    this@Activity_AjouterStock,
                                    message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                if (success) {
                                    setResult(Activity.RESULT_OK, Intent())
                                    finish()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

