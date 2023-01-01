package store.ingredientType

import atomics.ingredient.Ingredient

class InMemoryIngredientTypeStore(
    private val ingredientList: List<Ingredient> = createIngredientTypeList(),
): IngredientTypeStore {

    override suspend fun getAll(): List<Ingredient> {
        return ingredientList
    }

    override suspend fun add(ingredient: Ingredient): Boolean = false

    companion object {

        private fun createIngredientTypeList(): List<Ingredient> {
            return listOf(
                Ingredient.CHICKEN_BREAST,
                Ingredient.CHICKEN_THIGH,
                Ingredient.CHICKEN_WING,

                Ingredient.GARLIC,
            )
        }
    }
}