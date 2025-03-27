package com.example.gestionstockpoivronrouge.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "produit",indices = [androidx.room.Index(value = ["code"], unique = true)])
data class Produit(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val code: String ,
    val nom: String,
    val categorie: String,

)
