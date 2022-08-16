package store.image

import java.io.File

interface ImageStore {

    fun get(recipeId: String): File

    fun save(fileName: String, file: ByteArray)
}