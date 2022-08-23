package store.image

import io.github.crackthecodeabhi.kreds.connection.shutdown
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import store.DatabaseClients
import java.io.File

// save image as string
class RedisImageStore: ImageStore() {
    override suspend fun getOrNull(id: String): File? {
        var result: File? = null
        runBlocking {
            val job = launch(Dispatchers.Default) {
                DatabaseClients.redisClient.use { client ->
                    client.hget(imageMap, id)?.let {
                        result = File.createTempFile("temp", null)
                            .apply { writeBytes(it.decodeBase64Bytes()) }
                    }

                } // <--- the client/connection to redis is closed.
            }
            job.join() // wait for the co-routine to complete
        }
        return result
    }

    override suspend fun save(id: String, file: ByteArray) {
        runBlocking {
            val job = launch(Dispatchers.Default) {
                DatabaseClients.redisClient.use { client ->
                    client.hset(imageMap, id to file.encodeBase64())
                } // <--- the client/connection to redis is closed.
            }
            job.join() // wait for the co-routine to complete
        }
    }

    override fun shutDown() {
        runBlocking {
            shutdown() // shutdown the Kreds Event loop.
        }
    }

    companion object {

        private const val imageMap = "ImageMap"
    }

}