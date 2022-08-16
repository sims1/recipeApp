package store.image

import java.io.File

abstract class ImageStore {

    suspend fun get(id: String): File = getOrNull(id) ?: defaultImage

    abstract suspend fun getOrNull(id: String): File?

    abstract suspend fun save(id: String, file: ByteArray)

    open fun shutDown() {}

    companion object {

        private const val DEFAULT_IMAGE_NAME = "recipeImage/default.PNG"
        val defaultImage = File(this::class.java.classLoader.getResource(DEFAULT_IMAGE_NAME)!!.toURI())
    }

}