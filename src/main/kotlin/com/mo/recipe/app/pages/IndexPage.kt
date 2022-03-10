package com.mo.recipe.app.pages

import com.mo.recipe.app.components.index.FilterSidebar
import com.mo.recipe.app.components.index.RecipeTable
import com.mo.recipe.app.components.index.SelectedRecipesPanel
import com.mo.recipe.app.components.shared.Footer
import com.mo.recipe.app.components.shared.Header
import com.mo.recipe.app.recipes.atomics.Recipe
import com.mo.recipe.app.recipes.atomics.RecipeType
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
    var selectedRecipesState: Set<Recipe> by useState(emptySet())

    Header { }

    section {
        css {
            display = Display.flex
            flexDirection = FlexDirection.row
        }

        div {
            FilterSidebar {
                recipeTypes = RecipeType.values().toList()
                onSelectedType = { selectedType -> selectedTypesState += selectedType }
                onUnselectedType = { unselectedType -> selectedTypesState -= unselectedType }
            }
        }

        div {
            RecipeTable {
                recipes = InMemoryRecipeStore.getAll()
                selectedTypes = selectedTypesState
                onSelectRecipe = { recipe -> selectedRecipesState += recipe }
            }
        }

        div {
            SelectedRecipesPanel {
                selectedRecipes = selectedRecipesState
                onUnselectedRecipe = { recipe -> selectedRecipesState -= recipe  }
            }
        }
    }

    Footer { }

}