package store.ingredientType

import atomics.ingredient.Ingredient
import org.litote.kmongo.eq
import store.DatabaseClients

class MongoDBIngredientStore : IngredientStore {

    private val ingredientDatabase = DatabaseClients.mongoDBClient.getDatabase("Ingredients")
    private val ingredientCollection = ingredientDatabase.getCollection<Ingredient>()

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
