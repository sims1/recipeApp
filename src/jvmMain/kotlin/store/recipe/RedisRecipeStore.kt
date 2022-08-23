package store.recipe

import atomics.Recipe
import io.github.crackthecodeabhi.kreds.connection.shutdown
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import store.DatabaseClients

class RedisRecipeStore : RecipeStore {

    override suspend fun get(id: String): Recipe {
        var recipe: Recipe? = null
        runBlocking {
            val job = launch(Dispatchers.Default) {
                DatabaseClients.redisClient.use { client ->
                    val recipeString = client.hget(recipeMap, id)
                    recipe = Json.decodeFromString<Recipe>(recipeString!!)
                } // <--- the client/connection to redis is closed.
            }
            job.join() // wait for the co-routine to complete
        }
        return recipe!!
    }


    override suspend fun getAll(): List<Recipe> {
        var recipes: List<Recipe> = listOf()
        runBlocking {
            val job = launch(Dispatchers.Default) {
                DatabaseClients.redisClient.use { client ->
                    recipes = client.hgetAll(recipeMap)
                        .filter { it.startsWith("{") }
                        .map { recipeString -> Json.decodeFromString(recipeString) }
                } // <--- the client/connection to redis is closed.
            }
            job.join() // wait for the co-routine to complete
        }
        return recipes
    }

    override suspend fun add(recipe: Recipe): Boolean {
        runBlocking {
            val job = launch(Dispatchers.Default) {
                DatabaseClients.redisClient.use { client ->
                    client.hset(recipeMap, recipe.id to Json.encodeToString(recipe))
                } // <--- the client/connection to redis is closed.
            }
            job.join() // wait for the co-routine to complete
        }
        return true
    }

    override fun shutDown() {
        runBlocking {
            shutdown() // shutdown the Kreds Event loop.
        }
    }

    companion object {

        private const val recipeMap = "RecipeMap"
    }
}