package app.kl.testapp.api.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    @SerialName("data") val wallpapers: List<WallpaperData>,
    val meta: PaginationMeta
)