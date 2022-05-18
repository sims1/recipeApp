package store

import atomics.Recipe
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

class MongoDBStore {

    companion object {
        private val client = KMongo.createClient().coroutine
        private val recipesDatabase = client.getDatabase("recipes")
        private val recipesCollection = recipesDatabase.getCollection<Recipe>()

        suspend fun get(id: String): Recipe {
            return recipesCollection.findOne(Recipe::id eq id) as Recipe
        }

        suspend fun getAll() = recipesCollection.find().toList()

        // return true if inserted successfully
        // return false if already exists
        suspend fun add(recipe: Recipe): Boolean {
            return when (recipesCollection.findOne(Recipe::id eq recipe.id)) {
                null -> {
                    recipesCollection.insertOne(recipe)
                    true
                }
                else -> false
            }
        }
    }
}
