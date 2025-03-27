package com.example.gestionstockpoivronrouge.repository

import androidx.lifecycle.LiveData
import com.example.gestionstockpoivronrouge.dao.ProduitDao
import com.example.gestionstockpoivronrouge.model.Produit

class ProduitRepository(private val produitDao: ProduitDao) {

    val allProduits: LiveData<List<Produit>> = produitDao.getAllProducts()
    suspend fun ajouterProduit(produit: Produit) {
        produitDao.ajouterProduit(produit)
    }


    suspend fun modifierProduit(produit: Produit) {
        produitDao.modifierProduit(produit)
    }

    suspend fun supprimerProduit(produit: Produit) {
        produitDao.supprimerProduit(produit)
    }
}