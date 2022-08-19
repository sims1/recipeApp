package store.image

import kotlinx.coroutines.runBlocking
import store.withDurationPrinting
import java.io.File

// Testing on 111kb image
// redisImageStore.save(id, file) took 45 milli seconds
// mongoDBImageStore.save(id, file) took 69 milli seconds
// redisImageStore.getOrNull() took 36 milli seconds
// mongoDBImageStore.getOrNull() took 10 milli seconds
class TestingImageStore: ImageStore() {

    private val redisImageStore = RedisImageStore()
    private val mongoDBImageStore = MongoDBImageStore()

    override suspend fun getOrNull(id: String): File? {
        withDurationPrinting ("redisImageStore.getOrNull()") {
            runBlocking {
                redisImageStore.getOrNull(id)
            }
        }
        withDurationPrinting ("mongoDBImageStore.getOrNull()") {
            runBlocking {
                mongoDBImageStore.getOrNull(id)
            }
        }
        return null
    }

    override suspend fun save(id: String, file: ByteArray) {
        withDurationPrinting ("redisImageStore.save(id, file)") {
            runBlocking {
                redisImageStore.save(id, file)
            }
        }
        withDurationPrinting ("mongoDBImageStore.save(id, file)") {
            runBlocking {
                mongoDBImageStore.save(id, file)
            }
        }
    }

    override fun shutDown() {
        redisImageStore.shutDown()
        mongoDBImageStore.shutDown()
    }
}