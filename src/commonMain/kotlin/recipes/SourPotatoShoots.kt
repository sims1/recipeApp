package recipes

import atomics.*
import atomics.ingredient.IngredientDetails
import atomics.ingredient.IngredientType
import atomics.ingredient.SpiceAndSauceType

class SourPotatoShoots {

    companion object {
        val recipe = Recipe(
            "Sour Potato Shoots",
            listOf(
                IngredientDetails(IngredientType.POTATO)
            ),
            listOf(
                IngredientDetails(SpiceAndSauceType.VEGETABLE_OIL),
                IngredientDetails(SpiceAndSauceType.SALT),
                IngredientDetails(SpiceAndSauceType.BLACK_VINEGAR)
            ),
            listOf(Tag.CARB, Tag.VEGETABLE),
            "Oil + potato shoots\n"
                + "add black vinegar"
        )
    }
}