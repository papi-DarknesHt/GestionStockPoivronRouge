package com.example.gestionstockpoivronrouge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gestionstockpoivronrouge.model.Compte
import com.example.gestionstockpoivronrouge.repository.CompteRepository

class CompteViewModelFactory(private val repository: CompteRepository) : ViewModelProvider.Factory {
    val allcomptes: LiveData<List<Compte>> = repository.allcomptes
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CompteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}