package store.image

import atomics.RecipeImage
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import java.io.File

class MongoDBImageStore : ImageStore() {

    private val client = KMongo.createClient().coroutine
    private val database = client.getDatabase("recipeImage")
    private val collection = database.getCollection<RecipeImage>()

    override suspend fun getOrNull(id: String): File? {
        val recipeImage = collection.findOne(RecipeImage::id eq id) ?: return null
        return withContext(Dispatchers.IO) { File.createTempFile("temp", null) }
            .apply { writeBytes(recipeImage.image.decodeBase64Bytes()) }
    }

    override suspend fun save(id: String, file: ByteArray) {
        collection.save(RecipeImage(id, file.encodeBase64()))
    }

    override fun shutDown() {
        client.close()
    }
}