package atomics

import atomics.ingredient.IngredientDetails
import atomics.ingredient.Ingredient
import atomics.ingredient.Seasoning
import auth.LING
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Recipe(
    val name: String,
    val ingredients: List<IngredientDetails<Ingredient>>,
    val seasonings: List<IngredientDetails<Seasoning>>,
    val tags: List<Tag>,
    val cookingInstructions: String,
    val author: String = LING,
    @Contextual @SerialName("_id") val id: String = name.filter { !it.isWhitespace() }
) {
    private fun getTagsString() = tags.joinToString("\n") { it.value }

    fun createNewWithAuthor(newAuthor: String): Recipe {
        return Recipe(
            name = this.name,
            ingredients = this.ingredients,
            seasonings = this.seasonings,
            tags = this.tags,
            cookingInstructions = this.cookingInstructions,
            author = newAuthor,
            id = this.id
        )
    }
    fun getNameString() = name
    fun getIngredientsString() = ingredients.joinToString("\n") { it.getString() }
    fun getSeasoningsString() = seasonings.joinToString("\n") { it.getString() }

    override fun toString(): String {
        return """
            {
                Type: ${getTagsString()},
                Name: ${getNameString()},
                mainIngredients: ${getIngredientsString()},
                Seasoning: ${getSeasoningsString()},
                CookingInstructions: $cookingInstructions
            }
            """
    }

    companion object {
        const val create_path = "/createRecipe"
        const val create_picture_path = "/createPicture"
        const val add_ingredient = "/addIngredient"
        const val add_seasoning = "/addSeasoning"
        const val auth_path = "/auth"
        const val reauth_path = "/reauth"
        const val get_all_path = "/getAllRecipe"
        const val get_by_recipe_id_path = "/getbyrecipeid"
        const val get_image_by_recipe_id_path = "/getImageByRecipeId"
        const val get_ingredients_path = "/getIngredientsPath"
        const val get_seasoning_path = "/getSeasoningPath"

        const val load_recipe_from_in_file_path = "/loadRecipeFromInFile"
        const val load_recipe_from_in_memory_path = "/loadRecipeFromInMemory"
        const val load_ingredients_from_in_file_path = "/loadIngredientsFromInFile"
        const val load_seasoning_from_in_file_path = "/loadSeasoningFromInFile"
    }
}