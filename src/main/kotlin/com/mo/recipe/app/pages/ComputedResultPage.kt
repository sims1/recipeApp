package com.mo.recipe.app.pages

import com.mo.recipe.app.components.Footer
import com.mo.recipe.app.components.Header
import com.mo.recipe.app.components.ShoppingListTable
import com.mo.recipe.app.store.InMemoryRecipeStore
import csstype.NamedColor
import csstype.TextAlign.Companion.center
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.pre
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr
import react.router.useLocation

val ComputedResultPage = FC<Props> {
    Header { }

    val location = useLocation()
    val selectedRecipeIds = location.state as Array<String>
    val selectedRecipes = selectedRecipeIds.mapNotNull { recipeId -> InMemoryRecipeStore.get(recipeId) }

    ShoppingListTable {
        recipes = selectedRecipes
    }

    p { +"Cooking Instructions" }
    selectedRecipes.map { recipe ->
        div {
            css {
                backgroundColor = NamedColor.red
                textAlign = center
            }
            +recipe.getNameString()
            br { }
            +recipe.getVegetableAndMeatString()
            br { }
            +recipe.getSpicesAndSaucesString()
            br { }
            +recipe.getCookingInstructionsString()
            br { }
        }
    }

    Footer { }
}
