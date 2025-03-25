package com.example.gestionstockpoivronrouge.database

import com.example.gestionstockpoivronrouge.model.Compte
import com.example.gestionstockpoivronrouge.dao.CompteDao
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Compte::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun compteDao(): CompteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gestion_stock_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
