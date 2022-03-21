package com.mo.recipe.app.components.result

import com.mo.recipe.app.components.common.*
import com.mo.recipe.app.recipes.atomics.Recipe
import com.mo.recipe.app.recipes.atomics.VegetableAndMeatType
import com.mo.recipe.app.store.InMemoryRecipeStore
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
    var recipes: Map<Recipe, Int>
}

val ShoppingListTable = FC<ShoppingListTableProps> { props ->
    div {
        css {
            fontFamily = fancyH1FontFamilyAlias
            fontSize = h1FontSizeAlias
            fontWeight = h1FontWeightAlias
        }
        +"Shopping List"
    }

    div {
        css {
            marginTop = 1.pc
        }
        val neededVegetableAndMeatMap = mutableMapOf<VegetableAndMeatType, Int>()
        props.recipes.forEach { (recipe, recipeQuantity) ->
            recipe.vegetableAndMeat.forEach { ingredient ->
                val newQuantity = when (val currentQuantity = neededVegetableAndMeatMap[ingredient.type]) {
                    null -> ingredient.quantity * recipeQuantity
                    else -> ingredient.quantity * recipeQuantity + currentQuantity
                }
                neededVegetableAndMeatMap[ingredient.type] = newQuantity
            }
        }
        val neededVegetableAndMeat = neededVegetableAndMeatMap
            .map { (ingredient, quantity) -> "${ingredient.getValue()} x $quantity" }
            .joinToString("\n")

        val neededSpicesAndSauces = props.recipes.keys
            .flatMap { recipe -> recipe.spicesAndSauces }
            .map { ingredient -> ingredient.type }
            .distinct()
            .joinToString("\n") { ingredientType -> ingredientType.getValue() }

        table {
            css {
                margin = Auto.auto
                width = 50.pc
                padding = 2.pc
                backgroundColor = lightGreenAlias

                borderWidth = 1.px
                borderRadius = commonBorderRadiusAlias

                fontFamily = textFontFamilyAlias
                fontSize = textFontSizeAlias
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
