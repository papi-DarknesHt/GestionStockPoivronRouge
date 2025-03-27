package com.example.gestionstockpoivronrouge.repository

import androidx.lifecycle.LiveData
import com.example.gestionstockpoivronrouge.dao.StockDao
import com.example.gestionstockpoivronrouge.model.Stock

class StockRepository(private val stockDao: StockDao) {

    val allProduits: LiveData<List<Stock>> =stockDao.getAllStocks()
    suspend fun ajouterStock(stock: Stock) {
        stockDao.ajouterStock(stock)
    }


    suspend fun modifierStock(stock: Stock) {
        stockDao.modifierStock(stock)
    }

    suspend fun supprimerStock(stock: Stock) {
        stockDao.supprimerStock(stock)
    }
}