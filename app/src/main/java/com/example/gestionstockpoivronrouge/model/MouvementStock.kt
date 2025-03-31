package com.example.gestionstockpoivronrouge.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey


@Entity(tableName = "mouvement_stock",
    foreignKeys = [
        ForeignKey(
            entity = Produit::class,
                parentColumns = ["id"],
            childColumns = ["produit_id"],
            onDelete = CASCADE),
        ForeignKey(entity = Compte::class,
                parentColumns = ["id"],
            childColumns = ["utilisateur_id"],
            onDelete = CASCADE)
    ])
data class MouvementStock(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,

    @ColumnInfo(name = "produit_id")
    val  produitId: Int,

    @ColumnInfo(name = "utilisateur_id")
    val  utilisateurId : Int, // Qui a ajouté/modifié

    @ColumnInfo(name = "type_mouvement")
    val  typeMouvement : String,// "ENTREE" ou "SORTIE"

    @ColumnInfo(name = "quantite")
    val  quantite: Int,

    @ColumnInfo(name = "date_mouvement")
    val  dateMouvement : String, // Format : yyyy-MM-dd HH:mm:ss

    @ColumnInfo(name = "description")
    val  description : String // Justification des sorties
)
