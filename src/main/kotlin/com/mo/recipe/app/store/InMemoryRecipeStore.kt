package com.mo.recipe.app.store

import com.mo.recipe.app.recipes.*
import com.mo.recipe.app.recipes.atomics.Recipe

class InMemoryRecipeStore {

    companion object {
        fun get(id: String): Recipe? {
            val result = idToRecipe[id]
            if (result == null) {
                println("Error while getting recipe from id $id")
            }
            return idToRecipe[id]
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
        )

        private val idToRecipe: Map<String, Recipe> = createRecipeMap()

        private fun createRecipeMap(): Map<String, Recipe> {
            val result = mutableMapOf<String, Recipe>()
            recipeList.forEach { recipe -> result[recipe.id] = recipe }
            return result
        }
    }
}