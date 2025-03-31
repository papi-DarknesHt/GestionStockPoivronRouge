package com.example.gestionstockpoivronrouge.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
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
    val id : Int =0,
    @ColumnInfo(name = "id_produit")
    val id_produit: Int,
    @ColumnInfo(name = "Quantite")
    val qte: Int,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(id_produit)
        parcel.writeInt(qte)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Stock> {
        override fun createFromParcel(parcel: Parcel): Stock {
            return Stock(parcel)
        }

        override fun newArray(size: Int): Array<Stock?> {
            return arrayOfNulls(size)
        }
    }
}