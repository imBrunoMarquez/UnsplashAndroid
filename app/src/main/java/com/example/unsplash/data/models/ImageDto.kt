package com.example.unsplash.data.models



import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Marks the class as Parcelable, which allows it to be passed between components
// in an Android app using Intents or Bundles.
@Parcelize
data class ImageDto(

    //region Variables

    // These properties are serialized names from the JSON response
    @SerializedName("alt_description")
    val altDescription: String,
    @SerializedName("blur_hash")
    val blurHash: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("links")
    val links: Links,
    @SerializedName("promoted_at")
    val promotedAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("urls")
    val urls: Urls,
    @SerializedName("width")
    val width: Int

    //endregion

) : Parcelable {

    /**
     * Method to get needful {@link ImageData} from bulky {@link ImageDto} class.
     *
     * Method to convert this ImageDto object into an ImageData object,
     * which is a data class that represents an image stored in a Room database.
     * Returns a new ImageData object with the 'small' URL from the 'urls' property.
     */
    fun toImageData() =
        ImageData(urls.small ?: "")
}