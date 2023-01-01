package components.edit

import atomics.ingredient.IngredientDetails
import atomics.ingredient.Ingredient

class SelectedIngredientConfig(
    private val selected: Ingredient? = null,
    private val description: String? = null,
    private val quantity: Int? = 1
) {
    fun isValid() = (selected != null)

    fun getIngredient(): IngredientDetails<Ingredient> {
        return IngredientDetails(
            selected!!,
            description,
            quantity ?: 1
        )
    }

    fun newWithField(
        newSelected: Ingredient? = selected,
        newDescription: String? = description,
        newQuantity: Int? = quantity
    ): SelectedIngredientConfig {
        return SelectedIngredientConfig(
            selected = newSelected,
            description = newDescription,
            quantity = newQuantity,
        )
    }
}