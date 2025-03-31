package com.example.gestionstockpoivronrouge

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.gestionstockpoivronrouge.database.AppDatabase
import com.example.gestionstockpoivronrouge.repository.StockRepository
import com.example.gestionstockpoivronrouge.viewmodel.StockViewModel

class Activity_AjouterStock: AppCompatActivity() {
    private val stockViewModel : StockViewModel by viewModels{
        val daoStock = AppDatabase.getDatabase(this).stockDao()
        val repository = StockRepository(daoStock)
        StockViewModel.FactoryStock(repository)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ajout_stock_activity)
    }
}