package com.mo.recipe.app.components

import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.pre
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr
import com.mo.recipe.app.recipes.atomics.Recipe

external interface RecipeTableProps : Props {
    var recipes: List<Recipe>
}

val RecipeTable = FC<RecipeTableProps> { props ->
    table {
        tr {
            ReactHTML.th { +"Name" }
            ReactHTML.th { +"Ingredients" }
            ReactHTML.th { +"Spices and Sauces" }
            ReactHTML.th { +"Cooking Instructions" }
            ReactHTML.th { +"Type" }
        }
        props.recipes.map { recipe ->
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