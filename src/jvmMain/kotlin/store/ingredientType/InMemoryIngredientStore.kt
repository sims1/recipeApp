package store.ingredientType

import atomics.ingredient.Ingredient

class InMemoryIngredientStore(
    private val ingredientList: List<Ingredient> = create(),
): IngredientStore {

    override suspend fun getAll(): List<Ingredient> {
        return ingredientList
    }

    override suspend fun add(ingredient: Ingredient): Boolean = false

    companion object {

        private fun create(): List<Ingredient> {
            return listOf(
                Ingredient.CHICKEN_BREAST,
                Ingredient.CHICKEN_THIGH,
                Ingredient.CHICKEN_WING,

                Ingredient.GARLIC,
            )
        }
    }
}