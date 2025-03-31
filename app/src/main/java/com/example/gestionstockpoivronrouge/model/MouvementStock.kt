package com.example.gestionstockpoivronrouge.model

import androidx.room.ColumnInfo
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import androidx.room.Entity


@Entity(
    tableName = "mouvement_stock",
    foreignKeys = [ForeignKey(
        entity = Produit::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("produit_id"),
        onDelete = CASCADE
    ), ForeignKey(
        entity = Compte::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("utilisateur_id"),
        onDelete = CASCADE
    )]
)
class MouvementStock {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "produit_id")
    var produitId: Int = 0

    @ColumnInfo(name = "utilisateur_id")
    var utilisateurId: Int = 0 // Qui a ajouté/modifié

    @ColumnInfo(name = "type_mouvement")
    var typeMouvement: String? = null // "ENTREE" ou "SORTIE"

    @ColumnInfo(name = "quantite")
    var quantite: Int = 0

    @ColumnInfo(name = "date_mouvement")
    var dateMouvement: String? = null // Format : yyyy-MM-dd HH:mm:ss

    @ColumnInfo(name = "description")
    var description: String? = null // Justification des sorties
}
