package recipes

import atomics.*
import atomics.ingredient.Ingredient
import atomics.ingredient.IngredientType
import atomics.ingredient.SpiceAndSauceType

class MashedPotato {

    companion object {
        val recipe = Recipe(
            "Mashed Potato",
            listOf(
                Ingredient(IngredientType.POTATO),
                Ingredient(IngredientType.GARLIC)
            ),
            listOf(
                Ingredient(SpiceAndSauceType.BUTTER),
                Ingredient(SpiceAndSauceType.SALT),
                Ingredient(SpiceAndSauceType.PEPPER)
            ),
            listOf(Tag.CARB),
            "steam potato slices and 1 garlic\n" +
            "add all spices and mash\n"
        )
    }
}