package com.example.gestionstockpoivronrouge.database

import android.content.Context
import androidx.room.*
import com.example.gestionstockpoivronrouge.dao.CompteDao
import com.example.gestionstockpoivronrouge.dao.ProduitDao
import com.example.gestionstockpoivronrouge.dao.StockDao
import com.example.gestionstockpoivronrouge.model.Compte
import com.example.gestionstockpoivronrouge.model.Produit
import com.example.gestionstockpoivronrouge.model.Stock

@Database(
    entities = [Compte::class, Produit::class, Stock::class], // Ajout des entités Produit et Stock
    version = 2, // Augmenter la version pour refléter les changements
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun compteDao(): CompteDao
    abstract fun produitDao(): ProduitDao  // Ajout du DAO pour Produit
    abstract fun stockDao(): StockDao      // Ajout du DAO pour Stock

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gestion_stock1_db"
                )
                    .fallbackToDestructiveMigration() // Permet à Room de recréer la base si nécessaire
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
