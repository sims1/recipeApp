package recipes

import atomics.*
import atomics.ingredient.IngredientDetails
import atomics.ingredient.Ingredient
import atomics.ingredient.Seasoning

class SourPotatoShoots {

    companion object {
        val recipe = Recipe(
            "Sour Potato Shoots",
            listOf(
                IngredientDetails(Ingredient.POTATO)
            ),
            listOf(
                IngredientDetails(Seasoning.VEGETABLE_OIL),
                IngredientDetails(Seasoning.SALT),
                IngredientDetails(Seasoning.BLACK_VINEGAR)
            ),
            listOf(Tag.CARB, Tag.VEGETABLE),
            "Oil + potato shoots\n"
                + "add black vinegar"
        )
    }
}