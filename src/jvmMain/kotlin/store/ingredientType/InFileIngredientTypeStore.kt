package store.ingredientType

import atomics.ingredient.Ingredient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class InFileIngredientTypeStore(
    private val ingredientList: List<Ingredient> = createIngredientTypeList(),
): IngredientTypeStore {


    override suspend fun getAll(): List<Ingredient> {
        return ingredientList
    }

    override suspend fun add(ingredient: Ingredient): Boolean = false

    companion object {

        private fun createIngredientTypeList(): List<Ingredient> {
            val inputString = this::class.java.getResource("/IngredientTypeBackUp.txt").readText()
            return Json.decodeFromString(inputString)
        }
    }
}