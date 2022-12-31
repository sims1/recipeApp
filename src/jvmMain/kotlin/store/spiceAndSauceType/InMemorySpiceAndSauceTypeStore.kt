package store.spiceAndSauceType

import atomics.ingredient.SpiceAndSauceType

class InMemorySpiceAndSauceTypeStore(
    private val spiceAndSauceTypeList: List<SpiceAndSauceType> = create(),
): SpiceAndSauceTypeStore {

    override suspend fun getAll(): List<SpiceAndSauceType> {
        return spiceAndSauceTypeList
    }

    override suspend fun add(ingredientType: SpiceAndSauceType): Boolean = false

    companion object {

        private fun create(): List<SpiceAndSauceType> {
            return listOf(
                SpiceAndSauceType.VEGETABLE_OIL,
                SpiceAndSauceType.OLIVE_OIL,
                SpiceAndSauceType.SEASAME_OIL,
                SpiceAndSauceType.NUMB_SPICE_OIL,

                SpiceAndSauceType.SOY_SAUCE,
                SpiceAndSauceType.COOKING_WINE,
                SpiceAndSauceType.BLACK_VINEGAR,
                SpiceAndSauceType.BLACK_BEAN_CHILI_SAUCE,
                SpiceAndSauceType.BROAD_BEAN_SAUCE,

                SpiceAndSauceType.SALT,
                SpiceAndSauceType.HERBAMARE_SALT,
                SpiceAndSauceType.PEPPER,
                SpiceAndSauceType.CUMIN,
                SpiceAndSauceType.CHILI_POWDER,
                SpiceAndSauceType.DRY_CHILI,
                SpiceAndSauceType.CURRY_SPICE_BLEND,
                SpiceAndSauceType.CURRY_PASTE,

                SpiceAndSauceType.ITALIAN_HERB_SPICE_BLEND,
                SpiceAndSauceType.ORLEANS_SPICE_PACK,

                SpiceAndSauceType.BUTTER,
                SpiceAndSauceType.CORN_STARCH,
            )
        }
    }
}