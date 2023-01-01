package atomics.ingredient

import kotlinx.serialization.Serializable

@Serializable
class Seasoning(val name: String) : BaseIngredient {

    companion object {
        val VEGETABLE_OIL = Seasoning("Vegetable oil")
        val OLIVE_OIL = Seasoning("Olive Oil")
        val SEASAME_OIL = Seasoning("Seasame Oil(芝麻油)")
        val NUMB_SPICE_OIL = Seasoning("Numb Spice OIl(花椒油)")

        val SOY_SAUCE = Seasoning("Soy Sauce")
        val COOKING_WINE = Seasoning("Cooking Wine")
        val BLACK_VINEGAR = Seasoning("Black vinegar")
        val BLACK_BEAN_CHILI_SAUCE = Seasoning("Black Bean Chili Sauce(老干妈豆豉)")
        val BROAD_BEAN_SAUCE = Seasoning("Broad Bean Sauce(豆瓣酱)")

        val SALT = Seasoning("Salt")
        val HERBAMARE_SALT = Seasoning("Herbamare Salt")
        val PEPPER = Seasoning("Pepper")
        val CUMIN = Seasoning("Cumin(孜然)")
        val CHILI_POWDER = Seasoning("Chili Powder")
        val DRY_CHILI = Seasoning("Dry Chili")
        val CURRY_SPICE_BLEND = Seasoning("Curry spice blend")
        val CURRY_PASTE = Seasoning("Curry paste")

        val ITALIAN_HERB_SPICE_BLEND = Seasoning("Italian herb spice blend")
        val ORLEANS_SPICE_PACK = Seasoning("Orleans spice pack(新奥尔良)")

        val BUTTER = Seasoning("Butter")
        val CORN_STARCH = Seasoning("Corn Starch")
    }

    override fun getValue() = name
}