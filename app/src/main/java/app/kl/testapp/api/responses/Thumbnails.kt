package app.kl.testapp.api.responses

import kotlinx.serialization.Serializable

@Serializable
data class Thumbnails(
    val large: String,
    val original: String,
    val small: String
)