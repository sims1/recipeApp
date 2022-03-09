package com.mo.recipe.app.components.index

import ReactButton
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
    var onSelectRecipe: (Recipe) -> Unit
}

val RecipeTable = FC<RecipeTableProps> { props ->
    table {
        tbody { // this is mandatory, see https://github.com/facebook/react/issues/5652
            tr {
                th { }
                th { +"Name" }
                th { +"Vegetable and Meat" }
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
                    td {
                        ReactButton {
                            type = "primary"
                            onPress = { props.onSelectRecipe(recipe) }
                            +"select"
                        }
                    }
                    td { +recipe.getNameString() }
                    td { pre { +recipe.getVegetableAndMeatString() } }
                    td { pre { +recipe.getSpicesAndSaucesString() } }
                    td { pre { +recipe.getCookingInstructionsString() } }
                    td { +recipe.getTypeString() }
                }
            }
        }
    }
}