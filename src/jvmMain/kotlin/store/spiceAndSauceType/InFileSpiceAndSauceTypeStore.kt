package store.spiceAndSauceType

import atomics.ingredient.SpiceAndSauceType
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class InFileSpiceAndSauceTypeStore(
    private val spiceAndSauceTypeList: List<SpiceAndSauceType> = create(),
): SpiceAndSauceTypeStore {


    override suspend fun getAll(): List<SpiceAndSauceType> {
        return spiceAndSauceTypeList
    }

    override suspend fun add(spiceAndSauceType: SpiceAndSauceType): Boolean = false

    companion object {

        private fun create(): List<SpiceAndSauceType> {
            val inputString = this::class.java.getResource("/SpiceAndSauceTypeBackUp.txt").readText()
            return Json.decodeFromString(inputString)
        }
    }
}