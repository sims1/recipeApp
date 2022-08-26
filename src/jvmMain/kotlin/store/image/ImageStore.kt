package store.image

import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

abstract class ImageStore {

    suspend fun get(id: String): File = getOrNull(id) ?: defaultImage

    abstract suspend fun getOrNull(id: String): File?

    abstract suspend fun save(id: String, file: ByteArray)

    open fun shutDown() {}

    companion object {

        private const val DEFAULT_IMAGE_NAME = "recipeImage/default.PNG"
        val defaultImage = createDefaultImage()

        private fun createDefaultImage(): File {
            val inputStream = this::class.java.classLoader.getResourceAsStream(DEFAULT_IMAGE_NAME)!!
            val file = File.createTempFile("temp", null)
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING)
            return file
        }
    }

}