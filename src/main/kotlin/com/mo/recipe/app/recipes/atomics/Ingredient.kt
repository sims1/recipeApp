package com.mo.recipe.app.recipes.atomics

enum class VegetableAndMeatType(private val value: String) : BasedIngredient {
    CHICKEN_WING("chicken wing"),
    GARLIC("garlic"),
    POTATO("potato"),
    STEAK("steak"),
    SWEET_POTATO("sweet potato"),
    ZUCCHINI("zucchini")
    ;

    override fun getValue() = value
}

enum class SpiceAndSauceType(private val value: String) : BasedIngredient {
    BUTTER("butter"),
    ITALIAN_HERB_SPICE_BLEND("Italian herb spice blend"),
    PEPPER("pepper"),
    VEGETABLE_OIL("vegetable oil"),
    BLACK_VINEGAR("black vinegar"),
    SALT("salt"),
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
            result += " x $quantity $unit"
        }

        if (description != null) {
            result += " ($description)"
        }

        return result
    }
}