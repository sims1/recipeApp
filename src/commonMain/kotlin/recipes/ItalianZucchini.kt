package recipes

import atomics.*
import atomics.ingredient.IngredientDetails
import atomics.ingredient.Ingredient
import atomics.ingredient.SpiceAndSauceType

class ItalianZucchini {

    companion object {
        val recipe = Recipe(
            "Italian Zucchini",
            listOf(
                IngredientDetails(Ingredient.ZUCCHINI)
            ),
            listOf(
                IngredientDetails(SpiceAndSauceType.ITALIAN_HERB_SPICE_BLEND),
                IngredientDetails(SpiceAndSauceType.SALT),
                IngredientDetails(SpiceAndSauceType.PEPPER)
            ),
            listOf(Tag.VEGETABLE),
            "450F 12min"
        )
    }
}