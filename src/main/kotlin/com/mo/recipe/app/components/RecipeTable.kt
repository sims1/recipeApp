package com.mo.recipe.app.components

import react.FC
import react.Props
import react.dom.html.ReactHTML.pre
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr
import com.mo.recipe.app.recipes.atomics.Recipe
import com.mo.recipe.app.recipes.atomics.RecipeType
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.th

external interface RecipeTableProps : Props {
    var recipes: List<Recipe>
    var selectedTypes: Set<RecipeType>
}

val RecipeTable = FC<RecipeTableProps> { props ->
    table {
        tbody { // this is mandatory, see https://github.com/facebook/react/issues/5652
            tr {
                th { +"Name" }
                th { +"Ingredients" }
                th { +"Spices and Sauces" }
                th { +"Cooking Instructions" }
                th { +"Type" }
            }

            val showRecipes = when (props.selectedTypes.isEmpty()) {
                true -> props.recipes
                else -> props.recipes.filter { recipe -> recipe.type in props.selectedTypes }
            }

            showRecipes.map { recipe ->
                tr {
                    td { +recipe.getNameString() }
                    td { pre { +recipe.getIngredientsString() } }
                    td { pre { +recipe.getSpicesAndSaucesString() } }
                    td { pre { +recipe.getCookingInstructionsString() } }
                    td { +recipe.getTypeString() }
                }
            }
        }
    }
}