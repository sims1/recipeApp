package com.mo.recipe.app.recipes

import com.mo.recipe.app.recipes.atomics.RecipeType
import com.mo.recipe.app.recipes.atomics.VegetableAndMeatType
import com.mo.recipe.app.recipes.atomics.Recipe

class BakedSweetPotato {

    companion object {
        val recipe = Recipe(
            RecipeType.CARB,
            "Baked Sweet Potato",
            listOf(VegetableAndMeatType.SWEET_POTATO),
            listOf(),
            listOf(
                "using fork to poke holes on sweet potatoes",
                "put into cold oven",
                "425F 75min",
                "turn off oven, leave it in oven for another 30min"
            )
        )
    }
}