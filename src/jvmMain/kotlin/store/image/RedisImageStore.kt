package store.image

import io.github.crackthecodeabhi.kreds.connection.Endpoint
import io.github.crackthecodeabhi.kreds.connection.newClient
import io.github.crackthecodeabhi.kreds.connection.shutdown
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File


class RedisImageStore: ImageStore() {

    private val client = newClient(Endpoint.from("127.0.0.1:6379"))

    suspend fun createClient() {
        runBlocking {
            val job  = launch(Dispatchers.Default) {
                client.use { client ->
                    client.set("foo", "100")
                    // prints 101
                    println("incremented value of foo ${client.incr("foo")}")
                    client.expire("foo", 3u) // set expiration to 3 seconds
                    delay(3000)
                    assert(client.get("foo") == null)
                    println("done")
                } // <--- the client/connection to redis is closed.
            }
            job.join() // wait for the co-routine to complete
            shutdown() // shutdown the Kreds Event loop.
        }
    }

    override suspend fun getOrNull(id: String): File? {
        TODO("Not yet implemented")
    }

    override suspend fun save(fileName: String, file: ByteArray) {
        runBlocking {
            val job  = launch(Dispatchers.Default) {
                client.use { client ->

                } // <--- the client/connection to redis is closed.
            }
            job.join() // wait for the co-routine to complete
            shutdown() // shutdown the Kreds Event loop.
        }
    }


    override fun shutDown() {
        client.close()
    }

}