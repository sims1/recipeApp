package recipes

import atomics.*

class MashedPotato {

    companion object {
        val recipe = Recipe(
            "Mashed Potato",
            listOf(
                Ingredient(VegetableAndMeatType.POTATO),
                Ingredient(VegetableAndMeatType.GARLIC)
            ),
            listOf(
                Ingredient(SpiceAndSauceType.BUTTER),
                Ingredient(SpiceAndSauceType.SALT),
                Ingredient(SpiceAndSauceType.PEPPER)
            ),
            listOf(Tag.CARB),
            listOf(
                "steam potato slices and 1 garlic",
                "add all spices and mash"
            )
        )
    }
}