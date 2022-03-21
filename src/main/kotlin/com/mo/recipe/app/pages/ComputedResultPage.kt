package com.mo.recipe.app.pages

import com.mo.recipe.app.components.result.CookingDetails
import com.mo.recipe.app.components.shared.Footer
import com.mo.recipe.app.components.shared.Header
import com.mo.recipe.app.components.result.ShoppingListTable
import com.mo.recipe.app.deserialize
import com.mo.recipe.app.store.InMemoryRecipeStore
import csstype.Auto
import csstype.pc
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.body
import react.dom.html.ReactHTML.div
import react.router.useLocation

val ComputedResultPage = FC<Props> {
    Header { }

    val location = useLocation()
    val selectedRecipeIds = location.state as Array<String>
    println(selectedRecipeIds)
    println(1)
    val selectedRecipes = selectedRecipeIds.deserialize()
    println(2)

    body {
        div {
            css {
                margin = Auto.auto
                width = 60.pc
            }
            ShoppingListTable {
                recipes = selectedRecipes
            }
/*
            CookingDetails {
                recipes = selectedRecipes
            }
 */
        }
    }

    Footer { }
}
