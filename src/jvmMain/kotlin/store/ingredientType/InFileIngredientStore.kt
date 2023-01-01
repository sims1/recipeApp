package store.ingredientType

import atomics.ingredient.Ingredient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class InFileIngredientStore(
    private val ingredientList: List<Ingredient> = createIngredientList(),
): IngredientStore {


    override suspend fun getAll(): List<Ingredient> {
        return ingredientList
    }

    override suspend fun add(ingredient: Ingredient): Boolean = false

    companion object {

        private fun createIngredientList(): List<Ingredient> {
            val inputString = this::class.java.getResource("/IngredientsBackUp.txt").readText()
            return Json.decodeFromString(inputString)
        }
    }
}