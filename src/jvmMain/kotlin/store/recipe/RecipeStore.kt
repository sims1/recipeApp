package store.recipe

import atomics.Recipe

interface RecipeStore {

    suspend fun get(id: String): Recipe

    suspend fun getAll(): List<Recipe>

    // return true if inserted successfully or
    // return false if not inserted, might due to already exists or the store is read only
    suspend fun add(recipe: Recipe): Boolean

    fun shutDown() {}
}