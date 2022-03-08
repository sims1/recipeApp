package com.mo.recipe.app.recipes.atomics

enum class SpiceAndSauceType(val value: String) {
    BUTTER("butter"),
    ITALIAN_HERB_SPICE_BLEND("Italian herb spice blend"),
    PEPPER("pepper"),
    SALT("salt")
}

data class SpiceAndSauce(
    val type: SpiceAndSauceType,
    val description: String? = null,
    val quantity: Int = 1,
    val unit: CookingUnit = CookingUnit.DEFAULT
) {
    fun getString(): String {
        var result = type.value
        if (quantity != 1) {
            result += " x $quantity $unit"
        }

        if (description != null) {
            result += " ($description)"
        }

        return result
    }
}