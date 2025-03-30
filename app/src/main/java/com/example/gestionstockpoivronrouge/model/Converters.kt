package com.example.gestionstockpoivronrouge.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.io.IOException

class Converters {

    // Conversion de ByteArray en Bitmap
    @TypeConverter
    fun fromByteArray(byteArray: ByteArray?): Bitmap? {
        return byteArray?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
    }

    // Conversion de Bitmap en ByteArray
    @TypeConverter
    fun toByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream) // Compression à 50%
        return outputStream.toByteArray()
    }

}
