package com.example.gestionstockpoivronrouge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.gestionstockpoivronrouge.model.Compte
import com.example.gestionstockpoivronrouge.repository.CompteRepository

class CompteViewModel(private val repository: CompteRepository) : ViewModel() {

    val allComptes :LiveData<List<Compte>> = repository.allcomptes
    fun ajouterCompte(compte: Compte, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                repository.ajouterCompte(compte)
                onResult(true, "Compte ajouté avec succès")
            } catch (e: Exception) {
                onResult(false, e.message ?: "Erreur lors de l'ajout")
            }
        }
    }

    fun modifierCompte(compte: Compte, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            repository.modifierCompte(compte)
            onResult(true, "Compte modifié")
        }
    }

    fun supprimerCompte(compte: Compte, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            repository.supprimerCompte(compte)
            onResult(true, "Compte supprimé")
        }
    }

    fun listerComptes(onResult: (List<Compte>) -> Unit) {
        viewModelScope.launch {
            val comptes = repository.listerComptes()
            onResult(comptes)
        }
    }
}
