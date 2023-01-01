package store.ingredientType

import atomics.ingredient.Ingredient

interface IngredientStore {

    suspend fun getAll(): List<Ingredient>

    // return true if inserted successfully or
    // return false if not inserted, might due to already exists or the store is read only
    suspend fun add(ingredient: Ingredient): Boolean

    fun shutDown() {}
}