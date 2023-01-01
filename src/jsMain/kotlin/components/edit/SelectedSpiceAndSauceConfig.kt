package components.edit

import atomics.CookingUnit
import atomics.ingredient.IngredientDetails
import atomics.ingredient.Seasoning

class SelectedSpiceAndSauceConfig(
    private val selected: Seasoning? = null,
    private val description: String? = null,
    private val quantity: Int? = null,
    private val unit: CookingUnit? = null
) {
    fun isValid() = (selected != null)

    fun getIngredient(): IngredientDetails<Seasoning> {
        return IngredientDetails(
            selected!!,
            description,
            quantity ?: 1,
            unit ?: CookingUnit.DEFAULT
        )
    }

    fun newWithField(
        newSelected: Seasoning? = selected,
        newDescription: String? = description,
        newQuantity: Int? = quantity,
        newUnit: CookingUnit? = unit
    ): SelectedSpiceAndSauceConfig {
        return SelectedSpiceAndSauceConfig(
            selected = newSelected,
            description = newDescription,
            quantity = newQuantity,
            unit = newUnit
        )
    }
}