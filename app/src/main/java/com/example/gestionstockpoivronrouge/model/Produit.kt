package com.example.gestionstockpoivronrouge.model

import androidx.room.Entity

@Entity(tableName = "produit")
data class Produit(
    val code: String ,
    val nom: String,
    val categorie: String,

)
