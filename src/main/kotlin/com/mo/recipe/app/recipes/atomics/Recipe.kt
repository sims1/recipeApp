package com.mo.recipe.app.recipes.atomics

import kotlin.js.Date
import kotlin.random.Random

class Recipe(
    val type: RecipeType,
    private val name: String,
    private val ingredients: List<Ingredient>,
    private val spicesAndSauces: List<SpiceAndSauce>,
    private val cookingInstructions: List<String>,
    val id: String = Random.nextInt().toString() // \todo replace with npm uuid
) {
    fun getTypeString() = type.value
    fun getNameString() = name
    fun getIngredientsString() = ingredients.joinToString("\n") { item -> item.value }
    fun getSpicesAndSaucesString() = spicesAndSauces.joinToString("\n") { item -> item.value }
    fun getCookingInstructionsString() = cookingInstructions.joinToString("\n")

    override fun toString(): String {
        return """
            {
                Type: ${getTypeString()},
                Name: ${getNameString()},
                Ingredients: ${getIngredientsString()},
                SpicesAndSauces: ${getSpicesAndSaucesString()},
                CookingInstructions: ${getCookingInstructionsString()}
            }
            """
    }
}