package com.mo.recipe.app.recipes.atomics


val sortedVegetableAndMeatType = VegetableAndMeatType.values().sortedBy { it.getValue() }
val sortedSpiceAndSauceType = SpiceAndSauceType.values().sortedBy { it.getValue() }
enum class VegetableAndMeatType(private val value: String) : BasedIngredient {
    CHICKEN_WING("Chicken wing"),
    GARLIC("Garlic"),
    POTATO("Potato"),
    STEAK("Steak"),
    SWEET_POTATO("Sweet potato"),
    ZUCCHINI("Zucchini")
    ;

    override fun getValue() = value
}

enum class SpiceAndSauceType(private val value: String) : BasedIngredient {
    BUTTER("Butter"),
    ITALIAN_HERB_SPICE_BLEND("Italian herb spice blend"),
    PEPPER("Pepper"),
    VEGETABLE_OIL("Vegetable oil"),
    BLACK_VINEGAR("Black vinegar"),
    SALT("Salt"),
    ORLEANS_SPICE_PACK("Orleans spice pack(新奥尔良)"),
    ;

    override fun getValue() = value
}

data class Ingredient<out T : BasedIngredient>(
    val type: T,
    val description: String? = null,
    val quantity: Int = 1,
    val unit: CookingUnit = CookingUnit.DEFAULT
) {
    fun getString(): String {
        var result = type.getValue()
        if (quantity != 1) {
            result += " x $quantity ${unit.value}"
        }

        if (description != null) {
            result += " ($description)"
        }

        return result
    }
}