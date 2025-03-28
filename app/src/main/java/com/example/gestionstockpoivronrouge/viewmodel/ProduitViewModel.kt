package com.example.gestionstockpoivronrouge.viewmodel

import androidx.lifecycle.*
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
            repository.modifierProduit(produit)
            onResult(true, "Produit modifié")
        }
    }

    fun suprimmerProduit(produit: Produit, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            repository.modifierProduit(produit)
            onResult(true, "Produit supprimé")
        }
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