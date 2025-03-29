package com.example.gestionstockpoivronrouge.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "produit",indices = [androidx.room.Index(value = ["code"], unique = true)])
data class Produit(
    @PrimaryKey(autoGenerate = true)
    var id : Int=0,
    var code: String ,
    var nom: String,
    var categorie: String,

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nom)
        parcel.writeString(categorie)
        parcel.writeString(code)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Produit> {
        override fun createFromParcel(parcel: Parcel): Produit {
            return Produit(parcel)
        }

        override fun newArray(size: Int): Array<Produit?> {
            return arrayOfNulls(size)
        }
    }
}
