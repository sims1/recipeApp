package atomics.ingredient

import atomics.CookingUnit
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient<out T : BaseIngredient>(
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
