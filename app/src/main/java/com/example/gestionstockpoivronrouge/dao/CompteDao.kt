package com.example.gestionstockpoivronrouge.dao
import androidx.lifecycle.LiveData
import com.example.gestionstockpoivronrouge.model.Compte
import androidx.room.*
import androidx.room.Dao

@Dao
interface CompteDao {

    @Query("SELECT * FROM comptes")
    fun getAllComptes(): LiveData<List<Compte>>

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
