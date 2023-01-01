package components.edit

class AddCustomIngredientConfig(
    val name: String? = null,
    val ingredientType: AddIngredientType = AddIngredientType.OTHER_INGREDIENT
) {
    fun isValid() = (name != null)
    fun newWithField(
        newName: String? = name,
        newAddIngredientType: AddIngredientType = ingredientType,
    ): AddCustomIngredientConfig {
        return AddCustomIngredientConfig(
            name = newName,
            ingredientType = newAddIngredientType
        )
    }

    enum class AddIngredientType(val value: String) {
        MAIN_INGREDIENT("Main Ingredient"),
        OTHER_INGREDIENT("Other Ingredient"),
        SPICES_AND_SAUCE("Spices/Sauce")
    }
}