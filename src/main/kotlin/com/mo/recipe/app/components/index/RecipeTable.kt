package com.mo.recipe.app.components.index

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
import csstype.Cursor.Companion.pointer
import csstype.LineStyle.Companion.solid
import react.css.css
import react.dom.html.ButtonType
import react.dom.html.ReactHTML.button
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

    section {
        css {
            display = Display.flex
            flexWrap = FlexWrap.wrap
        }

        showRecipes.map { recipe ->
            var showDetailsState: Boolean by useState(false)
            div {
                css {
                    width = 15.pc
                    GridTemplateAreas(
                        GridArea("RecipeImage"),
                        GridArea("RecipeItem"),
                        GridArea("RecipeButton")
                    )
                    marginBottom = 8.pc
                }
                div {
                    css {
                        gridArea = GridArea("RecipeImage")
                    }
                    RecipeImage { }
                }
                div {
                    css {
                        gridArea = GridArea("RecipeItem")
                        paddingLeft = 0.6.pc
                        paddingRight = 0.6.pc
                        height = 5.pc
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
                }
                div {
                    css {
                        gridArea = GridArea("RecipeButton")
                    }
                    button {
                        css {
                            fontSize = textFontSizeAlias
                            backgroundColor = recipeNameColorAlias
                            color = buttonFontColor
                            borderStyle = None.none
                            borderRadius = commonButtonBorderRadiusAlias
                            paddingLeft = 3.pc
                            paddingRight = 3.pc
                            paddingTop = 0.5.pc
                            paddingBottom = 0.5.pc
                            cursor = pointer
                        }
                        type = ButtonType.button
                        onClick = { props.onSelectRecipe(recipe) }
                        +"Add"
                    }
                }
            }
        }
    }
}

private val RecipeImage = FC<Props> {
    img {
        src = "tasty_stuff.PNG"
        alt = "tasty stuff"
        height = 200.0
        width = 150.0
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
                fontSize = textFontSizeAlias
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
