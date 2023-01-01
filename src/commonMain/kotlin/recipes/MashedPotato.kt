package recipes

import atomics.*
import atomics.ingredient.IngredientDetails
import atomics.ingredient.Ingredient
import atomics.ingredient.Seasoning

class MashedPotato {

    companion object {
        val recipe = Recipe(
            "Mashed Potato",
            listOf(
                IngredientDetails(Ingredient.POTATO),
                IngredientDetails(Ingredient.GARLIC)
            ),
            listOf(
                IngredientDetails(Seasoning.BUTTER),
                IngredientDetails(Seasoning.SALT),
                IngredientDetails(Seasoning.PEPPER)
            ),
            listOf(Tag.CARB),
            "steam potato slices and 1 garlic\n" +
            "add all spices and mash\n"
        )
    }
}