/*package com.example.gestionstockpoivronrouge.repository

import androidx.lifecycle.LiveData
import com.example.gestionstockpoivronrouge.dao.MouvementStockDao
import com.example.gestionstockpoivronrouge.model.MouvementStock
import com.example.gestionstockpoivronrouge.model.Produit

class MouvementStockRepository(private val mouvementStockDao: MouvementStockDao) {

    val allMouvementStock :LiveData<List<MouvementStock>> = mouvementStockDao.getAllMouvementStocks()
    // Ajouter un produit (avec image)
    suspend fun ajouterProduit(mouvementStock: MouvementStock) {
        mouvementStockDao.ajouterMouvementStock(mouvementStock)
    }

    // Modifier un produit (avec image)
    suspend fun modifierProduit(mouvementStock: MouvementStock) {
        mouvementStockDao.modifierMouvementStock(mouvementStock)
    }

}*/