package com.example.gestionstockpoivronrouge.dao
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.gestionstockpoivronrouge.model.Compte
import com.example.gestionstockpoivronrouge.model.Produit

@Dao
interface ProduitDao {

    @Query("SELECT * FROM produit")
    fun getAllProducts(): LiveData<List<Produit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun ajouterProduit(produit: Produit)

    @Update
    suspend fun modifierProduit(produit: Produit)

    @Delete
    suspend fun supprimerProduit(produit: Produit)

    @Query("SELECT * FROM produit WHERE id = :produitId")
    fun getProduitById(produitId: Int): LiveData<Produit>





    /*@Query("SELECT * FROM comptes WHERE email = :email LIMIT 1")
    suspend fun getCompteParEmail(email: String): Compte?*/


}
