package com.example.gestionstockpoivronrouge.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.example.gestionstockpoivronrouge.model.Stock
import com.example.gestionstockpoivronrouge.repository.StockRepository

class StockViewModel(private val repository: StockRepository) : ViewModel() {

    val allStock: LiveData<List<Stock>> = repository.allStock
    fun ajouterStock(stock: Stock, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            repository.ajouterStock(stock)
            onResult(true, "Stock ajouté avec succès")
        }
    }

    fun modifierStock(stock: Stock, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            repository.modifierStock(stock)
            onResult(true, "Stock modifié")
        }
    }

    fun supprimerStock(stock: Stock, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            repository.supprimerStock(stock)
            onResult(true, "Stock supprimé")
        }
    }

    // Factory intégrée
    class FactoryStock(private val repository: StockRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StockViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StockViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
