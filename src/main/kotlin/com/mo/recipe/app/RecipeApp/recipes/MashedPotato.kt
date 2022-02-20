package com.mo.recipe.app.RecipeApp.recipes

import com.mo.recipe.app.RecipeApp.recipes.atomics.Recipe
import com.mo.recipe.app.RecipeApp.RecipeType
import com.mo.recipe.app.RecipeApp.recipes.atomics.Ingredient
import com.mo.recipe.app.RecipeApp.recipes.atomics.SpiceAndSauce

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
                SpiceAndSauce.BUTTER,
                SpiceAndSauce.SALT,
                SpiceAndSauce.PEPPER
            ),
            listOf(
                "steam potato slices and 1 garlic",
                "add all spices and mash"
            )
        )
    }
}