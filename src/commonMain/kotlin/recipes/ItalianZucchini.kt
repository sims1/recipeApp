package recipes

import atomics.*
import atomics.ingredient.Ingredient
import atomics.ingredient.IngredientType
import atomics.ingredient.SpiceAndSauceType

class ItalianZucchini {

    companion object {
        val recipe = Recipe(
            "Italian Zucchini",
            listOf(
                Ingredient(IngredientType.ZUCCHINI)
            ),
            listOf(
                Ingredient(SpiceAndSauceType.ITALIAN_HERB_SPICE_BLEND),
                Ingredient(SpiceAndSauceType.SALT),
                Ingredient(SpiceAndSauceType.PEPPER)
            ),
            listOf(Tag.VEGETABLE),
            "450F 12min"
        )
    }
}