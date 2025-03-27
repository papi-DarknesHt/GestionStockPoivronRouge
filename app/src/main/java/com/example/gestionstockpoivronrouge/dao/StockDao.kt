package com.example.gestionstockpoivronrouge.dao
import androidx.lifecycle.LiveData
import com.example.gestionstockpoivronrouge.model.Compte
import androidx.room.*
import androidx.room.Dao
import com.example.gestionstockpoivronrouge.model.Stock

@Dao
interface StockDao {

    @Query("SELECT * FROM stock")
    fun getAllStocks(): LiveData<List<Stock>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun ajouterStock(stock : Stock)

    @Update
    suspend fun modifierStock(stock :Stock)

    @Delete
    suspend fun supprimerStock(stock :Stock)



}
