package atomics.ingredient

import kotlinx.serialization.Serializable

@Serializable
class SpiceAndSauceType(val name: String) : BaseIngredient {

    companion object {
        val VEGETABLE_OIL = SpiceAndSauceType("Vegetable oil")
        val OLIVE_OIL = SpiceAndSauceType("Olive Oil")
        val SEASAME_OIL = SpiceAndSauceType("Seasame Oil(芝麻油)")
        val NUMB_SPICE_OIL = SpiceAndSauceType("Numb Spice OIl(花椒油)")

        val SOY_SAUCE = SpiceAndSauceType("Soy Sauce")
        val COOKING_WINE = SpiceAndSauceType("Cooking Wine")
        val BLACK_VINEGAR = SpiceAndSauceType("Black vinegar")
        val BLACK_BEAN_CHILI_SAUCE = SpiceAndSauceType("Black Bean Chili Sauce(老干妈豆豉)")
        val BROAD_BEAN_SAUCE = SpiceAndSauceType("Broad Bean Sauce(豆瓣酱)")

        val SALT = SpiceAndSauceType("Salt")
        val HERBAMARE_SALT = SpiceAndSauceType("Herbamare Salt")
        val PEPPER = SpiceAndSauceType("Pepper")
        val CUMIN = SpiceAndSauceType("Cumin(孜然)")
        val CHILI_POWDER = SpiceAndSauceType("Chili Powder")
        val DRY_CHILI = SpiceAndSauceType("Dry Chili")
        val CURRY_SPICE_BLEND = SpiceAndSauceType("Curry spice blend")
        val CURRY_PASTE = SpiceAndSauceType("Curry paste")

        val ITALIAN_HERB_SPICE_BLEND = SpiceAndSauceType("Italian herb spice blend")
        val ORLEANS_SPICE_PACK = SpiceAndSauceType("Orleans spice pack(新奥尔良)")

        val BUTTER = SpiceAndSauceType("Butter")
        val CORN_STARCH = SpiceAndSauceType("Corn Starch")
    }

    override fun getValue() = name
}