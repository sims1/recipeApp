package recipes

import atomics.*
import atomics.ingredient.Ingredient
import atomics.ingredient.IngredientType
import atomics.ingredient.SpiceAndSauceType

class PotatoShoots {

    companion object {
        val recipe = Recipe(
            "Potato Shoots",
            listOf(
                Ingredient(IngredientType.POTATO)
            ),
            listOf(
                Ingredient(SpiceAndSauceType.VEGETABLE_OIL),
                Ingredient(SpiceAndSauceType.SALT),
                Ingredient(SpiceAndSauceType.BLACK_VINEGAR)
            ),
            listOf(Tag.CARB, Tag.VEGETABLE),
            "cook!"
        )
    }
}