package com.mo.recipe.app.recipes

import com.mo.recipe.app.recipes.atomics.*

class MashedPotato {

    companion object {
        val recipe = Recipe(
            RecipeType.CARB,
            "Mashed Potato",
            listOf(
                VegetableAndMeatType.POTATO,
                VegetableAndMeatType.GARLIC
            ),
            listOf(
                Ingredient(SpiceAndSauceType.BUTTER),
                Ingredient(SpiceAndSauceType.SALT),
                Ingredient(SpiceAndSauceType.PEPPER)
            ),
            listOf(
                "steam potato slices and 1 garlic",
                "add all spices and mash"
            )
        )
    }
}