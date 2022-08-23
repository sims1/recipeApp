package store

import io.github.crackthecodeabhi.kreds.connection.Endpoint
import io.github.crackthecodeabhi.kreds.connection.newClient
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class DatabaseClients {

    private enum class DatabaseClientType {
        REDIS_CLIENT,
        MONGODB_CLIENT,
    }
    companion object {

        val redisClient by lazy {
            initializedClients[DatabaseClientType.REDIS_CLIENT] = true
            newClient(Endpoint.from("127.0.0.1:6379"))
        }

        val mongoDBClient by lazy {
            initializedClients[DatabaseClientType.MONGODB_CLIENT] = true
            KMongo.createClient().coroutine
        }

        private val initializedClients = mutableMapOf(
            DatabaseClientType.REDIS_CLIENT to false,
            DatabaseClientType.MONGODB_CLIENT to false,
        )

        fun shutDown() {
            if (initializedClients[DatabaseClientType.REDIS_CLIENT]!!) {
                redisClient.close()
            }

            if (initializedClients[DatabaseClientType.MONGODB_CLIENT]!!) {
                mongoDBClient.close()
            }
        }
    }

}