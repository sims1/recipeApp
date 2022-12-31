package store.spiceAndSauceType

import atomics.ingredient.SpiceAndSauceType
import org.litote.kmongo.eq
import store.DatabaseClients

class MongoDBSpiceAndSauceTypeStore : SpiceAndSauceTypeStore {

    private val spiceAndSauceTypeDatabase = DatabaseClients.mongoDBClient.getDatabase("spiceAndSauceType")
    private val spiceAndSauceTypeCollection = spiceAndSauceTypeDatabase.getCollection<SpiceAndSauceType>()

    override suspend fun getAll(): List<SpiceAndSauceType> = spiceAndSauceTypeCollection.find().toList()

    // return true if inserted successfully
    // return false if already exists
    override suspend fun add(spiceAndSauceType: SpiceAndSauceType): Boolean {
        return when (spiceAndSauceTypeCollection.findOne(SpiceAndSauceType::name eq spiceAndSauceType.name)) {
            null -> {
                spiceAndSauceTypeCollection.insertOne(spiceAndSauceType)
                true
            }
            else -> false
        }
    }

}
