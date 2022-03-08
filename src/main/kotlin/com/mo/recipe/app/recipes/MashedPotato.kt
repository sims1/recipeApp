package com.mo.recipe.app.recipes

import com.mo.recipe.app.recipes.atomics.*

class MashedPotato {

    companion object {
        val recipe = Recipe(
            RecipeType.CARB,
            "Mashed Potato",
            listOf(
                Ingredient.POTATO,
                Ingredient.GARLIC
            ),
            listOf(
                SpiceAndSauce(SpiceAndSauceType.BUTTER),
                SpiceAndSauce(SpiceAndSauceType.SALT),
                SpiceAndSauce(SpiceAndSauceType.PEPPER)
            ),
            listOf(
                "steam potato slices and 1 garlic",
                "add all spices and mash"
            )
        )
    }
}