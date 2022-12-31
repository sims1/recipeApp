package store.spiceAndSauceType

import atomics.ingredient.SpiceAndSauceType

interface SpiceAndSauceTypeStore {

    suspend fun getAll(): List<SpiceAndSauceType>

    // return true if inserted successfully or
    // return false if not inserted, might due to already exists or the store is read only
    suspend fun add(ingredientType: SpiceAndSauceType): Boolean

    fun shutDown() {}
}