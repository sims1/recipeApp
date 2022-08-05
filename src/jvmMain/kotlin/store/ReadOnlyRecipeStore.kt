package store

import atomics.Recipe
import atomics.Tag

abstract class ReadOnlyRecipeStore : RecipeStore {

    abstract val recipeList: List<Recipe>
    abstract val idToRecipe: Map<String, Recipe>

    override suspend fun get(id: String): Recipe {
        var result = idToRecipe[id]
        if (result == null) {
            println("Error while getting recipe from id $id")
            result = defaultRecipe
        }
        return result
    }

    override suspend fun getAll(): List<Recipe> = recipeList

    companion object {

        @JvmStatic
        protected fun List<Recipe>.toRecipeMap(): Map<String, Recipe> {
            val result = mutableMapOf<String, Recipe>()
            this.forEach { recipe -> result[recipe.id] = recipe }
            return result
        }

        private val defaultRecipe = Recipe(
            "Default Recipe",
            listOf(),
            listOf(),
            listOf(Tag.VEGETABLE),
            listOf(),
        )
    }
}