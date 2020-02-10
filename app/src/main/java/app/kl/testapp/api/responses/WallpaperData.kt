package app.kl.testapp.api.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WallpaperData(
    val id: String,
    val url: String,
    @SerialName("thumbs") val thumbnails: Thumbnails
)