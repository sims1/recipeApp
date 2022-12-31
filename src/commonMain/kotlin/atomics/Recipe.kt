package atomics

import atomics.ingredient.Ingredient
import atomics.ingredient.IngredientType
import atomics.ingredient.SpiceAndSauceType
import auth.LING
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Recipe(
    val name: String,
    val mainIngredients: List<Ingredient<IngredientType>>,
    val spicesAndSauces: List<Ingredient<SpiceAndSauceType>>,
    val tags: List<Tag>,
    val cookingInstructions: String,
    val author: String = LING,
    @Contextual @SerialName("_id") val id: String = name.filter { !it.isWhitespace() }
) {
    private fun getTagsString() = tags.joinToString("\n") { it.value }

    fun createNewWithAuthor(newAuthor: String): Recipe {
        return Recipe(
            name = this.name,
            mainIngredients = this.mainIngredients,
            spicesAndSauces = this.spicesAndSauces,
            tags = this.tags,
            cookingInstructions = this.cookingInstructions,
            author = newAuthor,
            id = this.id
        )
    }
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
        const val get_all_path = "/getAllRecipe"
        const val get_by_recipe_id_path = "/getbyrecipeid"
        const val get_image_by_recipe_id_path = "/getImageByRecipeId"
        const val get_ingredient_types_path = "/getIngredientTypesPath"

        const val load_recipe_from_in_file_path = "/loadRecipeFromInFile"
        const val load_recipe_from_in_memory_path = "/loadRecipeFromInMemory"
        const val load_ingredient_type_from_in_file_path = "/loadIngredientTypeFromInFile"
    }
}