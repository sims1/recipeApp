package com.mo.recipe.app.RecipeApp.recipes

import com.mo.recipe.app.RecipeApp.recipes.atomics.Recipe
import com.mo.recipe.app.RecipeApp.RecipeType
import com.mo.recipe.app.RecipeApp.recipes.atomics.Ingredient
import com.mo.recipe.app.RecipeApp.recipes.atomics.SpiceAndSauce

class ItalianZucchini {

    companion object {
        val recipe = Recipe(
            RecipeType.VEGETABLE,
            "Italian Zucchini",
            listOf(
                Ingredient.ZUCCHINI
            ),
            listOf(
                SpiceAndSauce.ITALIAN_HERB_SPICE_BLEND,
                SpiceAndSauce.SALT,
                SpiceAndSauce.PEPPER
            ),
            listOf("450F 12min")
        )
    }
}