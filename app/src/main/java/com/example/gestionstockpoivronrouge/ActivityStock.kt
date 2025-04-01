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
import com.example.gestionstockpoivronrouge.repository.StockRepository
import com.example.gestionstockpoivronrouge.viewmodel.StockViewModel
import com.example.gestionstockpoivronrouge.viewmodel.ProduitViewModel

class ActivityStock : AppCompatActivity() {

    private lateinit var activityStockAdapter: ActivityStockAdapter

    private val stockViewModel: StockViewModel by viewModels {
        val daoStock = AppDatabase.getDatabase(this).stockDao()
        val repository = StockRepository(daoStock)
        StockViewModel.FactoryStock(repository)
    }

    private val produitViewModel: ProduitViewModel by viewModels {
        val daoProduit = AppDatabase.getDatabase(this).produitDao()
        val repositoryProduit = ProduitRepository(daoProduit)
        ProduitViewModel.FactoryProduit(repositoryProduit)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)
        afficherStock()
    }

    private fun afficherStock() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewStock)

        // Initialisation de l'adaptateur
        activityStockAdapter = ActivityStockAdapter(
            context = this,
            stocks = mutableListOf(),
            onEditClick = { stock ->
                // Logique pour modifier le produit (si besoin)
                val intent = Intent(this, Activity_AjouterStock::class.java)
                intent.putExtra("stock", stock) // L'objet Stock est passé à l'autre activité
                startActivity(intent)
            },
            onDeleteClick = { stock ->
                // Logique pour supprimer le stock (à implémenter)
            },

            stockViewModel = stockViewModel,
            produitViewModel = produitViewModel,
            lifecycleOwner = this
        )

        recyclerView.apply {
            adapter = activityStockAdapter
            layoutManager = LinearLayoutManager(this@ActivityStock)
        }

        // Observer les données du ViewModel et mettre à jour la liste des stocks
        stockViewModel.allStock.observe(this, Observer { stocks ->
            if (stocks != null && stocks.isNotEmpty()) {
                activityStockAdapter.setStocks(stocks)
            } else {
                Toast.makeText(this, "Aucun stock disponible", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Gestion du menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_stock, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btAjoutStock -> {
                val intent = Intent(this, Activity_AjouterStock::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
