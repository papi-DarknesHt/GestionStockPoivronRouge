package com.example.gestionstockpoivronrouge.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "stock",
    foreignKeys = [
        ForeignKey(
            entity = Produit::class,
            parentColumns = ["id"],
            childColumns = ["id_produit"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Stock(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val id_produit: Int,
    val qte: Int,
)