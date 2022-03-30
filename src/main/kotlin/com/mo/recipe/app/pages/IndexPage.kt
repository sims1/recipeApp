package com.mo.recipe.app.pages

import com.mo.recipe.app.components.index.FilterSidebar
import com.mo.recipe.app.components.index.RecipeTable
import com.mo.recipe.app.components.index.SelectedRecipesPanel
import com.mo.recipe.app.components.shared.Footer
import com.mo.recipe.app.components.shared.Header
import com.mo.recipe.app.recipes.atomics.Recipe
import com.mo.recipe.app.recipes.atomics.RecipeType
import com.mo.recipe.app.recipes.atomics.VegetableAndMeatType
import com.mo.recipe.app.store.InMemoryRecipeStore
import csstype.*
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.section
import react.useState

// Following
// https://play.kotlinlang.org/hands-on/Building%20Web%20Applications%20with%20React%20and%20Kotlin%20JS/01_Introduction

val IndexPage = FC<Props> {
    var selectedTypesState: Set<RecipeType> by useState(emptySet())
    var selectedIngredientsState: Set<VegetableAndMeatType> by useState(emptySet())
    var selectedRecipesState: MutableMap<Recipe, Int> by useState(mutableMapOf())

    Header { }

    section {
        css {
            display = Display.grid
            gridTemplateAreas = GridTemplateAreas(
                GridArea("FilterSidebar RecipeTable SelectedRecipesPanel")
            )
        }

        div {
            css {
                gridArea = GridArea("FilterSidebar")
                textAlign = TextAlign.left
                width = 10.pc
            }
            FilterSidebar {
                recipeTypes = RecipeType.values().toList()
                onSelectedType = { selectedType -> selectedTypesState += selectedType }
                onUnselectedType = { unselectedType -> selectedTypesState -= unselectedType }
                ingredients = VegetableAndMeatType.values().toList()
                onSelectedIngredient = { selectedIngredient -> selectedIngredientsState += selectedIngredient }
                onUnselectedIngredient = { unselectedIngredient -> selectedIngredientsState -= unselectedIngredient }
            }
        }

        div {
            css {
                gridArea = GridArea("RecipeTable")
                width = 45.pc
                textAlign = TextAlign.center
            }
            RecipeTable {
                recipes = InMemoryRecipeStore.getAll()
                selectedTypes = selectedTypesState
                selectedIngredients = selectedIngredientsState
                onSelectRecipe = { recipe -> recipeIncrement(selectedRecipesState, recipe) }
            }
        }

        div {
            css {
                gridArea = GridArea("SelectedRecipesPanel")
                width = 25.pc
            }
            SelectedRecipesPanel {
                selectedRecipes = selectedRecipesState
                onRecipeIncrement = { recipe -> recipeIncrement(selectedRecipesState, recipe) }
                onRecipeDecrement = { recipe -> recipeDecrement(selectedRecipesState, recipe) }
            }
        }
    }

    Footer { }
}

private fun recipeIncrement(selectedRecipesState: MutableMap<Recipe, Int>, recipe: Recipe) {
    when (val numOfSelected = selectedRecipesState[recipe]) {
        null -> selectedRecipesState[recipe] = 1
        else -> selectedRecipesState[recipe] = numOfSelected + 1
    }
}

private fun recipeDecrement(selectedRecipesState: MutableMap<Recipe, Int>, recipe: Recipe) {
    when(val numOfSelected = selectedRecipesState[recipe]!!) {
        1 -> selectedRecipesState.remove(recipe)
        else -> selectedRecipesState[recipe] = numOfSelected - 1
    }
}