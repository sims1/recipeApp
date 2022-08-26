package atomics.ingredient

val sortedSpiceAndSauceType = SpiceAndSauceType.values().sortedBy { it.getValue() }

enum class SpiceAndSauceType(private val value: String) : BaseIngredient {
    BUTTER("Butter"),
    ITALIAN_HERB_SPICE_BLEND("Italian herb spice blend"),
    PEPPER("Pepper"),
    VEGETABLE_OIL("Vegetable oil"),
    BLACK_VINEGAR("Black vinegar"),
    SALT("Salt"),
    CHILI_POWDER("Chili Powder"),
    ORLEANS_SPICE_PACK("Orleans spice pack(新奥尔良)"),

    ;

    override fun getValue() = value
}