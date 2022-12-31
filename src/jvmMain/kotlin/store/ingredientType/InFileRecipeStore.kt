package store.ingredientType

import atomics.Recipe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
/*
class InFileRecipeStore(
    override val recipeList: List<Recipe> = createRecipeList(),
    override val idToRecipe: Map<String, Recipe> = recipeList.toRecipeMap()
) : ReadOnlyRecipeStore() {

    companion object {

        private fun createRecipeList(): List<Recipe> {
            val inputString = this::class.java.getResource("/RecipeBackUp.txt").readText()
            return Json.decodeFromString(inputString)
        }
    }
}*/