package store

import atomics.Recipe
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

class MongoDBRecipeStore : RecipeStore {

    private val client = KMongo.createClient().coroutine
    private val recipesDatabase = client.getDatabase("recipes")
    private val recipesCollection = recipesDatabase.getCollection<Recipe>()
        override suspend fun get(id: String): Recipe {
            return recipesCollection.findOne(Recipe::id eq id) as Recipe
        }

        override suspend fun getAll() = recipesCollection.find().toList()

        // return true if inserted successfully
        // return false if already exists
        override suspend fun add(recipe: Recipe): Boolean {
            return when (recipesCollection.findOne(Recipe::id eq recipe.id)) {
                null -> {
                    recipesCollection.insertOne(recipe)
                    true
                }
                else -> false
            }
        }
}
