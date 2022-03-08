package com.mo.recipe.app.recipes

import com.mo.recipe.app.recipes.atomics.*

class PotatoShoots {

    companion object {
        val recipe = Recipe(
            RecipeType.CARB,
            "Potato Shoots",
            listOf(
                Ingredient(VegetableAndMeatType.POTATO)
            ),
            listOf(
                Ingredient(SpiceAndSauceType.VEGETABLE_OIL),
                Ingredient(SpiceAndSauceType.SALT),
                Ingredient(SpiceAndSauceType.BLACK_VINEGAR)
            ),
            listOf(
                "cook!"
            )
        )
    }
}