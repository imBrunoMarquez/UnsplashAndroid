package com.example.unsplash.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

// Defines a data class that represents an image, which is stored in a Room database.
@Entity
@Parcelize
data class ImageData(
    // The primary key for this entity is the 'url' property.
    @PrimaryKey
    @SerializedName("url")
    val url: String
) : Parcelable {
    // This property is marked with '@ColumnInfo', which specifies that it should be stored
    // in the database as a separate column. This property is not used as part of the primary key.
    @IgnoredOnParcel
    @ColumnInfo
    var pageNum: Int = 1
}
