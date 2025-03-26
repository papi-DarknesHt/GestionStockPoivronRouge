package com.example.gestionstockpoivronrouge.repository

import androidx.lifecycle.LiveData
import com.example.gestionstockpoivronrouge.dao.CompteDao
import com.example.gestionstockpoivronrouge.model.Compte

class CompteRepository(private val compteDao: CompteDao) {

val allcomptes: LiveData<List<Compte>> = compteDao.getAllComptes()
    suspend fun ajouterCompte(compte: Compte) {
        val existant = compteDao.getCompteParEmail(compte.email)
        if (existant == null) {
            compteDao.ajouterCompte(compte)
        } else {
            throw Exception("Cet email est déjà utilisé !")
        }
    }

    suspend fun modifierCompte(compte: Compte) {
        compteDao.modifierCompte(compte)
    }

    suspend fun supprimerCompte(compte: Compte) {
        compteDao.supprimerCompte(compte)
    }

    fun listerComptes(): List<Compte> {
        return compteDao.listerComptes()
    }
}
