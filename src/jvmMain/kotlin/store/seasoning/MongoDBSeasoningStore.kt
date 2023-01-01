package store.seasoning

import atomics.ingredient.Seasoning
import org.litote.kmongo.eq
import store.DatabaseClients

class MongoDBSeasoningStore : SeasoningStore {

    private val seasoningDatabase = DatabaseClients.mongoDBClient.getDatabase("spiceAndSauceType")
    private val seasoningCollection = seasoningDatabase.getCollection<Seasoning>()

    override suspend fun getAll(): List<Seasoning> = seasoningCollection.find().toList()

    // return true if inserted successfully
    // return false if already exists
    override suspend fun add(seasoning: Seasoning): Boolean {
        return when (seasoningCollection.findOne(Seasoning::name eq seasoning.name)) {
            null -> {
                seasoningCollection.insertOne(seasoning)
                true
            }
            else -> false
        }
    }

}
