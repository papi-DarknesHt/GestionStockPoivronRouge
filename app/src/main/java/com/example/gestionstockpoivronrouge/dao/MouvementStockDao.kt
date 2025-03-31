package com.example.gestionstockpoivronrouge.dao

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.gestionstockpoivronrouge.model.MouvementStock
import com.example.gestionstockpoivronrouge.model.Stock

interface MouvementStockDao {

    @Query("SELECT * FROM mouvement_stock")
    fun getAllMouvementStocks(): LiveData<List<MouvementStock>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun ajouterMouvementStock(mouvementStock : MouvementStock)

    @Update
    suspend fun modifierMouvementStock(mouvementStock :MouvementStock)
}