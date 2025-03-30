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
        ),
        ForeignKey(
            entity = Compte::class,
            parentColumns = ["id"],
            childColumns = ["idCompte"],
            onDelete = ForeignKey.CASCADE
        )
    ])

data class Stock(
    @PrimaryKey(autoGenerate = true)
    val id : Int =0,
    val id_produit: Int,
    val qte: Int,
    val idCompte: Int
)