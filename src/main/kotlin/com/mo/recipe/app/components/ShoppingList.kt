package com.mo.recipe.app.components

import com.mo.recipe.app.recipes.atomics.Recipe
import react.FC
import react.Props
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.pre
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr

external interface ShoppingListTableProps : Props {
    var recipes: List<Recipe>
}

val ShoppingListTable = FC<ShoppingListTableProps> { props ->
    h1 { +"Shopping List" }

    val neededVegetableAndMeat = props.recipes
        .flatMap { recipe -> recipe.vegetableAndMeat }
        .groupBy { vegetableAndMeat -> vegetableAndMeat.type }
        .map { (vegetableAndMeatType, recipes) ->
            vegetableAndMeatType to recipes.sumOf { ingredient -> ingredient.quantity }
        }
        .map { (vegetableAndMeatType, quantity) -> "${vegetableAndMeatType.getValue()} x $quantity" }
        .joinToString("\n")

    br { }

    val neededSpicesAndSauces = props.recipes
        .flatMap { recipe -> recipe.spicesAndSauces }
        .map { spiceAndSauceType -> spiceAndSauceType.type.getValue() }
        .joinToString("\n")

    table {
        tbody {
            tr {
                td { pre { +neededVegetableAndMeat } }
                td { pre { +neededSpicesAndSauces } }
            }
        }
    }
}
