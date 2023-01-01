package store.spiceAndSauceType

import atomics.ingredient.Seasoning

interface SpiceAndSauceTypeStore {

    suspend fun getAll(): List<Seasoning>

    // return true if inserted successfully or
    // return false if not inserted, might due to already exists or the store is read only
    suspend fun add(ingredientType: Seasoning): Boolean

    fun shutDown() {}
}