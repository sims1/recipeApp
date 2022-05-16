package recipes

import atomics.*

class OvenSteak {

    companion object {
        val recipe = Recipe(
            RecipeType.MEAT,
            "Oven Steak",
            listOf(
                Ingredient(VegetableAndMeatType.STEAK)
            ),
            listOf(
                Ingredient(SpiceAndSauceType.SALT),
                Ingredient(SpiceAndSauceType.PEPPER)
            ),
            listOf(
                "leave it out of fridge for 30min to warm up to room temperature",
                "pat dry steak, add salt and pepper",
                "450F with cast iron pan",
                "3min one side, 3min the other side",
                "(medium rare 135-140 degrees)"
            )
        )
    }
}