package com.example.gestionstockpoivronrouge.viewmodel

import android.widget.Toast
import androidx.lifecycle.*
import com.example.gestionstockpoivronrouge.model.Compte
import com.example.gestionstockpoivronrouge.model.Produit
import com.example.gestionstockpoivronrouge.repository.ProduitRepository
import kotlinx.coroutines.launch

class ProduitViewModel(private val repository: ProduitRepository) : ViewModel() {
    val allProduit: LiveData<List<Produit>> = repository.allProduits
    fun ajouterProduits(produit: Produit, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                repository.ajouterProduit(produit)
                onResult(true, "Produit ajouté avec succès")
            } catch (e: Exception) {
                onResult(false, e.message ?: "Erreur lors de l'ajout")
            }
        }
    }

    fun modifierProduit(produit: Produit, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val test =repository.modifierProduit(produit)
            onResult(true, "Produit modifié")

        }
    }

    fun supprimerProduit(produit: Produit, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            repository.supprimerProduit(produit)

            onResult(true, "Produit supprimé")

        }
    }


    fun getAllProducts(): LiveData<List<Produit>> {
        return repository.getAllProducts()
    }





    class FactoryProduit(private val repository: ProduitRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProduitViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProduitViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}