package recipes

import atomics.*
import atomics.ingredient.IngredientDetails
import atomics.ingredient.Ingredient
import atomics.ingredient.Seasoning

class ItalianZucchini {

    companion object {
        val recipe = Recipe(
            "Italian Zucchini",
            listOf(
                IngredientDetails(Ingredient.ZUCCHINI)
            ),
            listOf(
                IngredientDetails(Seasoning.ITALIAN_HERB_SPICE_BLEND),
                IngredientDetails(Seasoning.SALT),
                IngredientDetails(Seasoning.PEPPER)
            ),
            listOf(Tag.VEGETABLE),
            "450F 12min"
        )
    }
}