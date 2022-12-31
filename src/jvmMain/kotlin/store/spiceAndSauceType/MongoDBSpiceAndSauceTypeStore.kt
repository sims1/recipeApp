package store.ingredientType

import atomics.ingredient.IngredientType
import org.litote.kmongo.eq
import store.DatabaseClients

class MongoDBIngredientTypeStore : IngredientTypeStore {

    private val ingredientTypeDatabase = DatabaseClients.mongoDBClient.getDatabase("ingredientType")
    private val ingredientTypeCollection = ingredientTypeDatabase.getCollection<IngredientType>()

    override suspend fun getAll(): List<IngredientType> = ingredientTypeCollection.find().toList()

    // return true if inserted successfully
    // return false if already exists
    override suspend fun add(ingredientType: IngredientType): Boolean {
        return when (ingredientTypeCollection.findOne(IngredientType::name eq ingredientType.name)) {
            null -> {
                ingredientTypeCollection.insertOne(ingredientType)
                true
            }
            else -> false
        }
    }

}
