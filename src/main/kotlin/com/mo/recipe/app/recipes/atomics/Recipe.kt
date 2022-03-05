package com.mo.recipe.app.recipes.atomics

class Recipe(
    private val type: RecipeType,
    private val name: String,
    private val ingredients: List<Ingredient>,
    private val spicesAndSauces: List<SpiceAndSauce>,
    private val cookingInstructions: List<String>,
) {
    fun getTypeString() = type.value
    fun getNameString() = name
    fun getIngredientsString() = ingredients.joinToString("\n") { item -> item.value }
    fun getSpicesAndSaucesString() = spicesAndSauces.joinToString("\n") { item -> item.value }
    fun getCookingInstructionsString() = cookingInstructions.joinToString("\n")
}