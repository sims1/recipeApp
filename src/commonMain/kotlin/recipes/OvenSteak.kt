package recipes

import atomics.*
import atomics.ingredient.IngredientDetails
import atomics.ingredient.Ingredient
import atomics.ingredient.SpiceAndSauceType

class OvenSteak {

    companion object {
        val recipe = Recipe(
            "Oven Steak",
            listOf(
                IngredientDetails(Ingredient.STEAK)
            ),
            listOf(
                IngredientDetails(SpiceAndSauceType.SALT),
                IngredientDetails(SpiceAndSauceType.PEPPER)
            ),
            listOf(Tag.BEEF),
            "leave it out of fridge for 30min to warm up to room temperature\n" +
                    "pat dry steak, add salt and pepper\n" +
                    "450F with cast iron pan\n" +
                    "3min one side, 3min the other side\n" +
                    "(medium rare 135-140 degrees)\n"
        )
    }
}