package store.ingredientType

import atomics.ingredient.Ingredient
import org.litote.kmongo.eq
import store.DatabaseClients

class MongoDBIngredientTypeStore : IngredientTypeStore {

    private val ingredientTypeDatabase = DatabaseClients.mongoDBClient.getDatabase("ingredientType")
    private val ingredientCollection = ingredientTypeDatabase.getCollection<Ingredient>()

    override suspend fun getAll(): List<Ingredient> = ingredientCollection.find().toList()

    // return true if inserted successfully
    // return false if already exists
    override suspend fun add(ingredient: Ingredient): Boolean {
        return when (ingredientCollection.findOne(Ingredient::name eq ingredient.name)) {
            null -> {
                ingredientCollection.insertOne(ingredient)
                true
            }
            else -> false
        }
    }

}
