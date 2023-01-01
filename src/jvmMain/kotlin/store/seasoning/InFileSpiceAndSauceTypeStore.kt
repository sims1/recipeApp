package store.seasoning

import atomics.ingredient.Seasoning
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class InFileSpiceAndSauceTypeStore(
    private val seasoningList: List<Seasoning> = create(),
): SpiceAndSauceTypeStore {


    override suspend fun getAll(): List<Seasoning> {
        return seasoningList
    }

    override suspend fun add(seasoning: Seasoning): Boolean = false

    companion object {

        private fun create(): List<Seasoning> {
            val inputString = this::class.java.getResource("/SpiceAndSauceTypeBackUp.txt").readText()
            return Json.decodeFromString(inputString)
        }
    }
}