package com.mo.recipe.app.pages

import com.mo.recipe.app.components.result.CookingInstructions
import com.mo.recipe.app.components.Footer
import com.mo.recipe.app.components.Header
import com.mo.recipe.app.components.result.ShoppingListTable
import com.mo.recipe.app.store.InMemoryRecipeStore
import react.FC
import react.Props
import react.router.useLocation

val ComputedResultPage = FC<Props> {
    Header { }

    val location = useLocation()
    val selectedRecipeIds = location.state as Array<String>
    val selectedRecipes = selectedRecipeIds.mapNotNull { recipeId -> InMemoryRecipeStore.get(recipeId) }

    ShoppingListTable {
        recipes = selectedRecipes
    }

    CookingInstructions {
        recipes = selectedRecipes
    }

    Footer { }
}
