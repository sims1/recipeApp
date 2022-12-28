package atomics.ingredient

val sortedSpiceAndSauceType = SpiceAndSauceType.values().sortedBy { it.getValue() }

enum class SpiceAndSauceType(private val value: String) : BaseIngredient {
    VEGETABLE_OIL("Vegetable oil"),
    OLIVE_OIL("Olive Oil"),
    SEASAME_OIL("Seasame Oil(芝麻油)"),
    NUMB_SPICE_OIL("Numb Spice OIl(花椒油)"),

    SOY_SAUCE("Soy Sauce"),
    COOKING_WINE("Cooking Wine(料酒)"),
    BLACK_VINEGAR("Black vinegar"),
    BLACK_BEAN_CHILI_SAUCE("Black Bean Chili Sauce(老干妈豆豉)"),
    BROAD_BEAN_SAUCE("Broad Bean Sauce(豆瓣酱)"),

    SALT("Salt"),
    HERBAMARE_SALT("Herbamare Salt"),
    PEPPER("Pepper"),
    CUMIN("Cumin(孜然)"),
    CHILI_POWDER("Chili Powder"),
    DRY_CHILI("Dry Chili"),
    CURRY_SPICE_BLEND("Curry spice blend"),
    CURRY_PASTE("Curry paste"),

    ITALIAN_HERB_SPICE_BLEND("Italian herb spice blend"),
    ORLEANS_SPICE_PACK("Orleans spice pack(新奥尔良)"),

    BUTTER("Butter"),
    CORN_STARCH("Corn Starch"),
    ;

    override fun getValue() = value
}