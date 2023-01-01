package store.spiceAndSauceType

import atomics.ingredient.Seasoning

class InMemorySpiceAndSauceTypeStore(
    private val seasoningList: List<Seasoning> = create(),
): SpiceAndSauceTypeStore {

    override suspend fun getAll(): List<Seasoning> {
        return seasoningList
    }

    override suspend fun add(ingredientType: Seasoning): Boolean = false

    companion object {

        private fun create(): List<Seasoning> {
            return listOf(
                Seasoning.VEGETABLE_OIL,
                Seasoning.OLIVE_OIL,
                Seasoning.SEASAME_OIL,
                Seasoning.NUMB_SPICE_OIL,

                Seasoning.SOY_SAUCE,
                Seasoning.COOKING_WINE,
                Seasoning.BLACK_VINEGAR,
                Seasoning.BLACK_BEAN_CHILI_SAUCE,
                Seasoning.BROAD_BEAN_SAUCE,

                Seasoning.SALT,
                Seasoning.HERBAMARE_SALT,
                Seasoning.PEPPER,
                Seasoning.CUMIN,
                Seasoning.CHILI_POWDER,
                Seasoning.DRY_CHILI,
                Seasoning.CURRY_SPICE_BLEND,
                Seasoning.CURRY_PASTE,

                Seasoning.ITALIAN_HERB_SPICE_BLEND,
                Seasoning.ORLEANS_SPICE_PACK,

                Seasoning.BUTTER,
                Seasoning.CORN_STARCH,
            )
        }
    }
}