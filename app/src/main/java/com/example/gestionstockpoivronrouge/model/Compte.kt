package com.example.gestionstockpoivronrouge.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comptes",indices = [androidx.room.Index(value = ["email"], unique = true)])
data class Compte(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nom: String,
    val prenom: String,
    val email: String,
    @ColumnInfo(name = "statut", defaultValue = "manager")
    val statut: String, // Admin, manager
    @ColumnInfo(name = "password", defaultValue = "admin")
    val password: String
)
