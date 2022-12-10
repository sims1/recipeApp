package atomics

import atomics.ingredient.Ingredient
import atomics.ingredient.IngredientType
import atomics.ingredient.SpiceAndSauceType
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Recipe(
    private val name: String,
    val mainIngredients: List<Ingredient<IngredientType>>,
    val spicesAndSauces: List<Ingredient<SpiceAndSauceType>>,
    val tags: List<Tag>,
    val cookingInstructions: String,
    @Contextual @SerialName("_id") val id: String = name.filter { !it.isWhitespace() }
) {
    private fun getTagsString() = tags.joinToString("\n") { it.value }
    fun getNameString() = name
    fun getVegetableAndMeatString() = mainIngredients.joinToString("\n") { it.getString() }
    fun getSpicesAndSaucesString() = spicesAndSauces.joinToString("\n") { it.getString() }

    override fun toString(): String {
        return """
            {
                Type: ${getTagsString()},
                Name: ${getNameString()},
                VegetableAndMeat: ${getVegetableAndMeatString()},
                SpicesAndSauces: ${getSpicesAndSaucesString()},
                CookingInstructions: $cookingInstructions
            }
            """
    }

    companion object {
        const val create_path = "/create"
        const val create_picture_path = "/createpicture"
        const val auth_path = "/auth"
        const val reauth_path = "/reauth"
        const val get_all_path = "/getall"
        const val load_from_in_file_path = "/loadfrominfile"
        const val load_from_in_memory_path = "/loadfrominmemory"
        const val get_by_recipe_id_path = "/getbyrecipeid"
        const val get_image_by_recipe_id_path= "/getImageByRecipeId"
    }
}