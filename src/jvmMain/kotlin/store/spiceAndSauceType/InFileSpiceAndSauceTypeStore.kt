package store.ingredientType

import atomics.ingredient.IngredientType
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class InFileIngredientTypeStore(
    private val ingredientTypeList: List<IngredientType> = createIngredientTypeList(),
): IngredientTypeStore {


    override suspend fun getAll(): List<IngredientType> {
        return ingredientTypeList
    }

    override suspend fun add(ingredientType: IngredientType): Boolean = false
    companion object {

        private fun createIngredientTypeList(): List<IngredientType> {
            val inputString = this::class.java.getResource("/IngredientTypeBackUp.txt").readText()
            return Json.decodeFromString(inputString)
        }
    }
}