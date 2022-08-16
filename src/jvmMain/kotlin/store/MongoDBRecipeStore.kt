package store

import atomics.Recipe
import atomics.RecipeImage
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

class MongoDBRecipeStore : RecipeStore {

    private val client = KMongo.createClient().coroutine

    private val recipeDatabase = client.getDatabase("recipes")
    private val recipeCollection = recipeDatabase.getCollection<Recipe>()

    override suspend fun get(id: String): Recipe {
        return recipeCollection.findOne(Recipe::id eq id) as Recipe
    }

    override suspend fun getAll() = recipeCollection.find().toList()

    // return true if inserted successfully
    // return false if already exists
    override suspend fun add(recipe: Recipe): Boolean {
        return when (recipeCollection.findOne(Recipe::id eq recipe.id)) {
            null -> {
                recipeCollection.insertOne(recipe)
                true
            }
            else -> false
        }
    }

    override fun shutDown() {
        client.close()
    }

}
