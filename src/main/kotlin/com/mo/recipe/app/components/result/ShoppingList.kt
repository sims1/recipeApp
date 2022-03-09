package com.mo.recipe.app.components.result

import com.mo.recipe.app.recipes.atomics.Recipe
import csstype.*
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr

external interface ShoppingListTableProps : Props {
    var recipes: List<Recipe>
}

val ShoppingListTable = FC<ShoppingListTableProps> { props ->
    h1 {
        css {
            fontFamily = FontFamily.cursive
        }
        +"Shopping List"
    }

    div {
        val neededVegetableAndMeat = props.recipes
            .flatMap { recipe -> recipe.vegetableAndMeat }
            .groupBy { vegetableAndMeat -> vegetableAndMeat.type }
            .map { (vegetableAndMeatType, recipes) ->
                vegetableAndMeatType to recipes.sumOf { ingredient -> ingredient.quantity }
            }
            .joinToString("\n") {
                    (vegetableAndMeatType, quantity) -> "${vegetableAndMeatType.getValue()} x $quantity"
            }

        val neededSpicesAndSauces = props.recipes
            .flatMap { recipe -> recipe.spicesAndSauces }
            .joinToString("\n") {
                    spiceAndSauceType -> spiceAndSauceType.type.getValue()
            }

        table {
            css {
                margin = Auto.auto
                width = 50.pc
                padding = 2.pc
                backgroundColor = rgb(152, 251, 152)

                borderWidth = 1.px
                borderRadius = 10.px

                fontFamily = FontFamily.monospace
                whiteSpace = WhiteSpace.preWrap
                fontSize = 1.5.em
            }

            tbody {
                tr {
                    td { +neededVegetableAndMeat }
                    td { +neededSpicesAndSauces }
                }
            }
        }
    }
}
