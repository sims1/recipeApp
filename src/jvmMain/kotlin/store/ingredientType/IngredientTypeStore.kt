package store.ingredientType

import atomics.ingredient.IngredientType

interface IngredientTypeStore {

    suspend fun getAll(): List<IngredientType>

    // return true if inserted successfully or
    // return false if not inserted, might due to already exists or the store is read only
    suspend fun add(ingredientType: IngredientType): Boolean

    fun shutDown() {}
}