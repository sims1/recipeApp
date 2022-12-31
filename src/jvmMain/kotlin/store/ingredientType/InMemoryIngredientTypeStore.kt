package store.ingredientType

import atomics.ingredient.IngredientType

class InMemoryIngredientTypeStore(
    private val ingredientTypeList: List<IngredientType> = createIngredientTypeList(),
): IngredientTypeStore {

    override suspend fun getAll(): List<IngredientType> {
        return ingredientTypeList
    }

    override suspend fun add(ingredientType: IngredientType): Boolean = false

    companion object {

        private fun createIngredientTypeList(): List<IngredientType> {
            return listOf(
                IngredientType.CHICKEN_BREAST,
                IngredientType.CHICKEN_THIGH,
                IngredientType.CHICKEN_WING,

                IngredientType.GARLIC,
            )
        }
    }
}