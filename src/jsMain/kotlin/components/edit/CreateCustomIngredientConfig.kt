package components.edit

class CreateCustomIngredientConfig(
    val name: String? = null,
    val ingredient: CreateIngredientType = CreateIngredientType.OTHER_INGREDIENT
) {
    fun isValid() = (name != null)
    fun newWithField(
        newName: String? = name,
        newCreateIngredientType: CreateIngredientType = ingredient,
    ): CreateCustomIngredientConfig {
        return CreateCustomIngredientConfig(
            name = newName,
            ingredient = newCreateIngredientType
        )
    }

    enum class CreateIngredientType(val value: String) {
        MAIN_INGREDIENT("Main Ingredient"),
        OTHER_INGREDIENT("Other Ingredient"),
        SPICES_AND_SAUCE("Spices/Sauce")
    }
}