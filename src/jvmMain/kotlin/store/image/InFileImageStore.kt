package store.image

import java.io.File

class InFileImageStore: ImageStore {

    override fun get(recipeId: String): File {
        val imageUrl = this::class.java.classLoader.getResource("$DEFAULT_IMAGE_DIR/$recipeId.PNG") ?: return defaultImage
        val result = File(imageUrl.toURI())
        return when {
            result.exists() && !result.isDirectory -> result
            else -> defaultImage
        }
    }

    override fun save(fileName: String, file: ByteArray) {
        File("$IMAGE_DIR/$fileName").writeBytes(file)
    }

    companion object {

        private const val DEFAULT_IMAGE_DIR = "recipeImage"
        private const val DEFAULT_IMAGE_NAME = "$DEFAULT_IMAGE_DIR/default.PNG"
        private val defaultImage = File(this::class.java.classLoader.getResource(DEFAULT_IMAGE_NAME).toURI())

        private const val IMAGE_DIR = "/Users/linghe/Desktop/recipeImages"
    }
}
