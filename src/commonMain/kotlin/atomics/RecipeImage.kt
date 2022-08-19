package atomics

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RecipeImage(
    @Contextual @SerialName("_id") val id: String,
    val image: String
)