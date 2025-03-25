package com.example.gestionstockpoivronrouge.dao
import com.example.gestionstockpoivronrouge.model.Compte
import androidx.room.*

@Dao
interface CompteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun ajouterCompte(compte: Compte)

    @Update
    suspend fun modifierCompte(compte: Compte)

    @Delete
    suspend fun supprimerCompte(compte: Compte)

    @Query("SELECT * FROM comptes ORDER BY nom ASC")
    fun listerComptes(): List<Compte>

    @Query("SELECT * FROM comptes WHERE email = :email LIMIT 1")
    suspend fun getCompteParEmail(email: String): Compte?
}
