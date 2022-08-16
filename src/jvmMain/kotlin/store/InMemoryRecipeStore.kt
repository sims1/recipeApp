package store

import atomics.Recipe
import recipes.*

class InMemoryRecipeStore(
    override val recipeList: List<Recipe> = createRecipeList(),
    override val idToRecipe: Map<String, Recipe> = recipeList.toRecipeMap()
) : ReadOnlyRecipeStore() {

    companion object {

        private fun createRecipeList(): List<Recipe> {
            val result = listOf(
                OrleansChickenWing.recipe,
                BakedSweetPotato.recipe,
                ItalianZucchini.recipe,
                MashedPotato.recipe,
                PotatoShoots.recipe,
                OvenSteak.recipe,
            )

            result.groupBy { it.id }
                .values
                .forEach { recipes ->
                    if (recipes.size > 1) {
                        println("Warning: recipes has duplicated id ${recipes[0].id}")
                    }
                }

            return result
        }
    }
}