package com.mo.recipe.app.recipes.atomics

import com.mo.recipe.app.RecipeApp.RecipeType

class Recipe(
    var type: RecipeType,
    var name: String,
    var ingredients: List<Ingredient>,
    var spicesAndSauces: List<SpiceAndSauce>,
    var cookingInstructions: List<String>,
)