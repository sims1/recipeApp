package com.mo.recipe.app.recipes

import com.mo.recipe.app.recipes.atomics.*

class ItalianZucchini {

    companion object {
        val recipe = Recipe(
            RecipeType.VEGETABLE,
            "Italian Zucchini",
            listOf(
                Ingredient(VegetableAndMeatType.ZUCCHINI)
            ),
            listOf(
                Ingredient(SpiceAndSauceType.ITALIAN_HERB_SPICE_BLEND),
                Ingredient(SpiceAndSauceType.SALT),
                Ingredient(SpiceAndSauceType.PEPPER)
            ),
            listOf("450F 12min")
        )
    }
}