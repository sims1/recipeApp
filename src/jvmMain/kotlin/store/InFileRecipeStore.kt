package store

import atomics.Recipe
import java.io.File
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

class InFileRecipeStore(
    override val recipeList: List<Recipe> = createRecipeList(),
    override val idToRecipe: Map<String, Recipe> = recipeList.toRecipeMap()
) : ReadOnlyRecipeStore() {

    companion object {

        private fun createRecipeList(): List<Recipe> {
            val s = System.getProperty("user.dir")
            println(s)
            val inputString = File(s,"src/RecipeBackUp.txt")
                .inputStream()
                .bufferedReader()
                .use { it.readText() }
            println(inputString)
            return Json.decodeFromString(inputString)
        }
    }
}