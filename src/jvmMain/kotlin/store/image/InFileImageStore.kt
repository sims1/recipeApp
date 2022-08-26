package store.image

import java.io.File

class InFileImageStore: ImageStore() {
    override suspend fun getOrNull(id: String): File? {
        val imageUrl = this::class.java.classLoader.getResource("$DEFAULT_IMAGE_DIR/$id.PNG") ?: return null
        val result = File(imageUrl.toURI())
        return when {
            result.exists() && !result.isDirectory -> result
            else -> null
        }
    }

    override suspend fun save(id: String, file: ByteArray) {
        File("$IMAGE_DIR/$id").writeBytes(file)
    }

    companion object {
        private const val DEFAULT_IMAGE_DIR = "recipeImage"
        private const val IMAGE_DIR = "/home/ling/recipe"
            //"/Users/linghe/Desktop/recipeImages"
    }
}
