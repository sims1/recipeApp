package com.mo.recipe.app.components.index

import ReactButton
import com.mo.recipe.app.components.common.*
import react.FC
import react.Props
import react.dom.html.ReactHTML.pre
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr
import com.mo.recipe.app.recipes.atomics.Recipe
import com.mo.recipe.app.recipes.atomics.RecipeType
import csstype.*
import csstype.LineStyle.Companion.solid
import react.css.css
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.th
import react.useState

external interface RecipeTableProps : Props {
    var recipes: List<Recipe>
    var selectedTypes: Set<RecipeType>
    var onSelectRecipe: (Recipe) -> Unit
}

val RecipeTable = FC<RecipeTableProps> { props ->
    val showRecipes = when (props.selectedTypes.isEmpty()) {
        true -> props.recipes
        else -> props.recipes.filter { recipe -> recipe.type in props.selectedTypes }
    }

    showRecipes.map { recipe ->
        var showDetailsState: Boolean by useState(false)

        div {
            div {
                RecipeItem {
                    recipeItem = recipe
                    showDetails = showDetailsState
                    onMouseEnterInfo = {
                        showDetailsState = true
                    }
                    onMouseLeaveInfo = {
                        showDetailsState = false
                    }
                }
            }
            div {
                ReactButton {
                    type = "primary"
                    onPress = { props.onSelectRecipe(recipe) }
                    +"select"
                }
            }
        }
    }
}


private interface RecipeItemProps : Props {
    var recipeItem: Recipe
    var showDetails: Boolean
    var onMouseEnterInfo: () -> Unit
    var onMouseLeaveInfo: () -> Unit
}

private val RecipeItem = FC<RecipeItemProps> { props ->
    div {
        p {
            css {
                fontFamily = h2FontFamilyAlias
                fontSize = h2FontSizeAlias
            }
            +"${props.recipeItem.getNameString()} "
            span {
                css {
                    onMouseEnter = { props.onMouseEnterInfo() }
                    onMouseLeave = { props.onMouseLeaveInfo() }

                    fontFamily = textFontFamilyAlias
                    fontSize = textFontSizeAlias
                    backgroundColor = NamedColor.lightgrey
                    borderRadius = commonBorderRadiusAlias
                }
                +" info "
                if (props.showDetails) {
                    RecipeHoverBox {
                        recipeitem = props.recipeItem
                    }
                }
            }
        }
    }
}

private interface RecipeHoverBoxProps : Props {
    var recipeitem: Recipe
}

private val RecipeHoverBox = FC<RecipeHoverBoxProps> { props ->
    table {
        css {
            position = Position.absolute
            backgroundColor = hoverColorAlias
            fontSize = unimportantFontSizeAlias
            fontFamily = textFontFamilyAlias

            borderColor = NamedColor.lightgrey
            borderStyle = solid
            borderCollapse = BorderCollapse.collapse
        }
        tbody { // this is mandatory, see https://github.com/facebook/react/issues/5652
            tr {
                css {
                    textAlign = TextAlign.left

                    borderColor = NamedColor.lightgrey
                    borderStyle = solid
                    borderCollapse = BorderCollapse.collapse
                }
                th { +"Ingredients" }
                th { +"" }
                th { +"Cooking Instructions" }
            }
            tr {
                css {
                    borderColor = NamedColor.lightgrey
                    borderStyle = solid
                    borderCollapse = BorderCollapse.collapse
                }
                td { pre { +props.recipeitem.getVegetableAndMeatString() } }
                td { pre { +props.recipeitem.getSpicesAndSaucesString() } }
                td { pre { +props.recipeitem.getCookingInstructionsString() } }
            }
        }
    }
}
