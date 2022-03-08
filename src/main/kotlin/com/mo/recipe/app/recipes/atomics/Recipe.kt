package com.mo.recipe.app.recipes.atomics

import kotlin.random.Random

class Recipe(
    val type: RecipeType,
    private val name: String,
    val vegetableAndMeat: List<Ingredient<VegetableAndMeatType>>,
    val spicesAndSauces: List<Ingredient<SpiceAndSauceType>>,
    private val cookingInstructions: List<String>,
    val id: String = Random.nextInt().toString() // \todo replace with npm uuid
) {
    fun getTypeString() = type.value
    fun getNameString() = name
    fun getVegetableAndMeatString() = vegetableAndMeat.joinToString("\n") { it.getString() }
    fun getSpicesAndSaucesString() = spicesAndSauces.joinToString("\n") { it.getString() }
    fun getCookingInstructionsString() = cookingInstructions.joinToString("\n")

    override fun toString(): String {
        return """
            {
                Type: ${getTypeString()},
                Name: ${getNameString()},
                VegetableAndMeat: ${getVegetableAndMeatString()},
                SpicesAndSauces: ${getSpicesAndSaucesString()},
                CookingInstructions: ${getCookingInstructionsString()}
            }
            """
    }
}