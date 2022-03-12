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
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.section
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

        section {
            css {
                display = Display.flex
                flexWrap = FlexWrap.wrap
            }
            div {
                img {
                    css {
                        margin = 1.em
                    }
                    src = "tasty_stuff.PNG"
                    alt = "tasty stuff"
                    height = 300.0
                    width = 200.0
                }
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
                    fontSize = unimportantFontSizeAlias
                    color = NamedColor.grey
                    borderRadius = commonBorderRadiusAlias
                }
                +"ⓘ"
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

            borderStyle = solid
            borderCollapse = BorderCollapse.collapse
            borderRadius = commonBorderRadiusAlias
        }
        tbody { // this is mandatory, see https://github.com/facebook/react/issues/5652
            tr {
                css {
                    borderColor = NamedColor.lightgrey
                    borderStyle = solid
                }
                th { +"Main Ingredients" }
                td { pre { +props.recipeitem.getVegetableAndMeatString() } }
            }
            tr {
                css {
                    borderColor = NamedColor.lightgrey
                    borderStyle = solid
                }
                th { +"Stocked Ingredients" }
                td { pre { +props.recipeitem.getSpicesAndSaucesString() } }
            }
            tr {
                css {
                    borderColor = NamedColor.lightgrey
                    borderStyle = solid
                }
                th { +"Cooking Instructions" }
                td { pre { +props.recipeitem.getCookingInstructionsString() } }
            }
        }
    }
}
