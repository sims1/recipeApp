package store.image

import java.io.File

interface ImageStore {

    fun get(recipeId: String): File
}