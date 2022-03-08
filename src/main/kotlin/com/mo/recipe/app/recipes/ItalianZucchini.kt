package com.mo.recipe.app.recipes

import com.mo.recipe.app.recipes.atomics.*

class ItalianZucchini {

    companion object {
        val recipe = Recipe(
            RecipeType.VEGETABLE,
            "Italian Zucchini",
            listOf(
                Ingredient.ZUCCHINI
            ),
            listOf(
                SpiceAndSauce(SpiceAndSauceType.ITALIAN_HERB_SPICE_BLEND),
                SpiceAndSauce(SpiceAndSauceType.SALT),
                SpiceAndSauce(SpiceAndSauceType.PEPPER)
            ),
            listOf("450F 12min")
        )
    }
}