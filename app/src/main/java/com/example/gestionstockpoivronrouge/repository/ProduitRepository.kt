package com.example.gestionstockpoivronrouge.repository

import androidx.lifecycle.LiveData
import com.example.gestionstockpoivronrouge.dao.ProduitDao
import com.example.gestionstockpoivronrouge.model.Compte
import com.example.gestionstockpoivronrouge.model.Produit

class ProduitRepository(private val produitDao: ProduitDao) {

    // Récupérer tous les produits
    val allProduits: LiveData<List<Produit>> = produitDao.getAllProducts()

    fun getAllProducts(): LiveData<List<Produit>> {
        return produitDao.getAllProducts()
    }


    // Ajouter un produit (avec image)
    suspend fun ajouterProduit(produit: Produit) {
        produitDao.ajouterProduit(produit)
    }

    // Modifier un produit (avec image)
    suspend fun modifierProduit(produit: Produit) {
        produitDao.modifierProduit(produit)
    }

    // Supprimer un produit
    suspend fun supprimerProduit(produit: Produit) {
        produitDao.supprimerProduit(produit)
    }


}
