/*package com.example.gestionstockpoivronrouge.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.gestionstockpoivronrouge.model.MouvementStock

// Added this import statement
@Dao // Added this annotation
interface MouvementStockDao {
    @get:Query("SELECT * FROM mouvement_stock")
    val allMouvementStocks: LiveData<List<MouvementStock?>?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun ajouterMouvementStock(mouvementStock: MouvementStock?)

    @Update
    fun modifierMouvementStock(mouvementStock: MouvementStock?)
}*/