package com.example.gestionstockpoivronrouge

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionstockpoivronrouge.database.AppDatabase
import com.example.gestionstockpoivronrouge.repository.StockRepository
import com.example.gestionstockpoivronrouge.viewmodel.StockViewModel

class ActivityStock:AppCompatActivity() {
    private lateinit var activityStockAdapter :ActivityStockAdapter
    private val stockViewModel : StockViewModel by viewModels{
        val daoStock = AppDatabase.getDatabase(this).stockDao()
        val repository = StockRepository(daoStock)
        StockViewModel.FactoryStock(repository)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)
        afficherStock()
    }

    private fun afficherStock() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewStock)

        activityStockAdapter = ActivityStockAdapter(
            this,
            mutableListOf(),
            onEditClick = { stock ->
                // Logique pour modifier le produit
                val intent = Intent(this, Activity_AjouterStock::class.java)
                intent.putExtra("stock", stock) // L'objet Produit est passé à l'autre activité
                startActivity(intent)
            },
            onDeleteClick = { stock ->

            },
            stockViewModel = stockViewModel
        )

        recyclerView.adapter = activityStockAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        stockViewModel.allStock.observe(this, Observer { stocks ->
            stocks?.let {
                activityStockAdapter.setStocks(it)  // Met à jour la liste des produits dans l'adaptateur
            }
        })
    }


    //    menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_stock, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btAjoutStock -> {
                val activity_AjouterStock = Intent(this, Activity_AjouterStock::class.java)
                startActivity(activity_AjouterStock)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}