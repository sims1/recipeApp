package store.image

import io.github.crackthecodeabhi.kreds.connection.Endpoint
import io.github.crackthecodeabhi.kreds.connection.KredsClient
import io.github.crackthecodeabhi.kreds.connection.newClient
import io.github.crackthecodeabhi.kreds.connection.shutdown
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

// save image as string
class RedisImageStore: ImageStore() {

    private val client: KredsClient = newClient(Endpoint.from("127.0.0.1:6379"))
    override suspend fun getOrNull(id: String): File? {
        var result: File? = null
        runBlocking {
            val job = launch(Dispatchers.Default) {
                client.use { client ->
                    client.get(id)?.let {
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
                client.use { client ->
                    client.set(id, file.encodeBase64())
                } // <--- the client/connection to redis is closed.
            }
            job.join() // wait for the co-routine to complete
        }
    }

    override fun shutDown() {
        runBlocking {
            client.close()
            shutdown() // shutdown the Kreds Event loop.
        }
    }

}