package com.mo.recipe.app.components.result

import com.mo.recipe.app.components.common.*
import com.mo.recipe.app.recipes.atomics.Recipe
import csstype.*
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr

external interface ShoppingListTableProps : Props {
    var recipes: List<Recipe>
}

val ShoppingListTable = FC<ShoppingListTableProps> { props ->
    div {
        css {
            fontFamily = fancyHeaderFontFamilyAlias
            fontSize = headerFontSizeAlias
            fontWeight = headerFontWeightAlias
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
                borderRadius = commonBorderRadiusAlias

                fontFamily = recipeFontFamilyAlias
                fontSize = recipeFontSizeAlias
                whiteSpace = WhiteSpace.preWrap
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
