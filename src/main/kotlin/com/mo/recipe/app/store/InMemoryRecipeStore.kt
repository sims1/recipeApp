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

        fun getAll(): List<Recipe> = recipeList

        private val recipeList: List<Recipe> = createRecipeList()
        private val idToRecipe: Map<String, Recipe> = createRecipeMap()

        private fun createRecipeList(): List<Recipe> {
            val result = listOf(
                BakedSpicedChickenWing.recipe,
                BakedSweetPotato.recipe,
                ItalianZucchini.recipe,
                MashedPotato.recipe,
                PotatoShoots.recipe,
                OvenSteak.recipe,
            )

            result.groupBy { it.id }
                .values
                .forEach{ recipes ->
                    if (recipes.size > 1) {
                        println("Warning: recipes has duplicated id ${recipes[0].id}")
                    }
                }

            return result
        }

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