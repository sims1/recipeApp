package com.mo.recipe.app.store

import com.mo.recipe.app.recipes.*
import com.mo.recipe.app.recipes.atomics.*
import kotlin.random.Random

class InMemoryRecipeStore {

    companion object {
        fun get(id: String): Recipe {
            var result = idToRecipe[id]
            if (result == null) {
                println("Error while getting recipe from id $id")
                result = defaultRecipe
            }
            return result
        }

        fun getAll(): List<Recipe> {
            return recipeList
        }

        private val recipeList: List<Recipe> = listOf(
            BakedSpicedChickenWing.recipe,
            BakedSweetPotato.recipe,
            ItalianZucchini.recipe,
            MashedPotato.recipe,
            PotatoShoots.recipe,
            OvenSteak.recipe,
        )

        private val idToRecipe: Map<String, Recipe> = createRecipeMap()

        private fun createRecipeMap(): Map<String, Recipe> {
            val result = mutableMapOf<String, Recipe>()
            recipeList.forEach { recipe -> result[recipe.id] = recipe }
            return result
        }

        private val defaultRecipe = Recipe(
            RecipeType.MEAT,
            "Default Recipe",
            listOf(),
            listOf(),
            listOf(),
        )
    }
}