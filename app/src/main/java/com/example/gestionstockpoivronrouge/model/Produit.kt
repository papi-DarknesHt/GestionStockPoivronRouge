package com.example.gestionstockpoivronrouge.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "produit", indices = [androidx.room.Index(value = ["code"], unique = true)])
@Parcelize
data class Produit(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val code: String,
    val nom: String,
    val categorie: String,
    val imagePath: String? = null
) : Parcelable
