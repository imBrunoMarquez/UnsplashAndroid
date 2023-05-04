package com.example.unsplash.data.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Marks the class as Parcelable, which allows it to be passed between components
// in an Android app using Intents or Bundles.
@Parcelize
data class Urls(
    // These properties are serialized names from the JSON response
    @SerializedName("full")
    val full: String?,
    @SerializedName("raw")
    val raw: String?,
    @SerializedName("regular")
    val regular: String?,
    @SerializedName("small")
    val small: String?,
    @SerializedName("thumb")
    val thumb: String?
) : Parcelable
