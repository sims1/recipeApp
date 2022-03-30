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
import com.mo.recipe.app.recipes.atomics.VegetableAndMeatType
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
    var selectedIngredients: Set<VegetableAndMeatType>
    var onSelectRecipe: (Recipe) -> Unit
}

val RecipeTable = FC<RecipeTableProps> { props ->
    var showRecipes = props.recipes
    if (props.selectedTypes.isNotEmpty()) {
        showRecipes = props.recipes.filter { recipe -> recipe.type in props.selectedTypes }
    }

    if (props.selectedIngredients.isNotEmpty()) {
        showRecipes = props.recipes.filter { recipe ->
            val ingredients = recipe.vegetableAndMeat.map { ingredient -> ingredient.type }
            props.selectedIngredients.intersect(ingredients).isNotEmpty()
        }
    }

    section {
        css {
            display = Display.flex
            flexWrap = FlexWrap.wrap
        }

        var showRecipeDetailsState: Recipe? by useState(null)
        showRecipes.map { recipe ->
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
                    div {
                        p {
                            css {
                                fontFamily = h2FontFamilyAlias
                                fontSize = textFontSizeAlias
                            }
                            +"${recipe.getNameString()} "
                            span {
                                css {
                                    onMouseEnter = {
                                        showRecipeDetailsState = recipe
                                        println("showRecipeDetailsState = recipe")
                                    }
                                    onMouseLeave = {
                                        showRecipeDetailsState = null
                                        println("showRecipeDetailsState = null")
                                    }

                                    fontFamily = textFontFamilyAlias
                                    fontSize = unimportantFontSizeAlias
                                    color = NamedColor.grey
                                    borderRadius = commonBorderRadiusAlias
                                }
                                +"ⓘ"
                                if (showRecipeDetailsState?.id == recipe.id) {
                                    RecipeHoverBox {
                                        recipeitem = recipe
                                    }
                                }
                            }
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
