package com.example.gestionstockpoivronrouge.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "comptes", indices = [androidx.room.Index(value = ["email"], unique = true)])
@Parcelize
data class Compte(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nom: String,

    val prenom: String,

    val email: String,
    @ColumnInfo(name = "statut")
    val statut: String = "manager", // Valeur par défaut dans le constructeur

    @ColumnInfo(name = "password")
    val password: String = "admin" // Valeur par défaut dans le constructeur
) : Parcelable
