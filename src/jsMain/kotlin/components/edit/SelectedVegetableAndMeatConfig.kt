package components.edit

import atomics.ingredient.Ingredient
import atomics.ingredient.IngredientType

class SelectedVegetableAndMeatConfig(
    private val selected: IngredientType? = null,
    private val description: String? = null,
    private val quantity: Int? = 1
) {
    fun isValid() = (selected != null)

    fun getIngredient(): Ingredient<IngredientType> {
        return Ingredient(
            selected!!,
            description,
            quantity ?: 1
        )
    }

    fun newWithField(
        newSelected: IngredientType? = selected,
        newDescription: String? = description,
        newQuantity: Int? = quantity
    ): SelectedVegetableAndMeatConfig {
        return SelectedVegetableAndMeatConfig(
            selected = newSelected,
            description = newDescription,
            quantity = newQuantity,
        )
    }
}