package components.index

import api.recipeIdParameterKey
import atomics.Recipe
import atomics.RecipeType
import atomics.VegetableAndMeatType
import react.FC
import react.Props
import react.dom.html.ReactHTML.pre
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr
import components.common.*
import csstype.*
import csstype.Cursor.Companion.pointer
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
    var allRecipes: List<Recipe>
    var selectedTypes: Set<RecipeType>
    var selectedIngredients: Set<VegetableAndMeatType>
    var onSelectRecipe: (Recipe) -> Unit
}

val RecipeTable = FC<RecipeTableProps> { props ->
    var showRecipes = props.allRecipes
    if (props.selectedTypes.isNotEmpty()) {
        showRecipes = showRecipes.filter { recipe -> recipe.type in props.selectedTypes }
    }

    if (props.selectedIngredients.isNotEmpty()) {
        showRecipes = showRecipes.filter { recipe ->
            val ingredients = recipe.vegetableAndMeat.map { ingredient -> ingredient.type }
            props.selectedIngredients.intersect(ingredients.toSet()).isNotEmpty()
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
                    RecipeImage { recipeId = recipe.id }
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
                                    }
                                    onMouseLeave = {
                                        showRecipeDetailsState = null
                                    }

                                    fontFamily = textFontFamilyAlias
                                    fontSize = unimportantFontSizeAlias
                                    color = unimportantColorAlias
                                    borderRadius = commonBorderRadiusAlias
                                }
                                +"???"
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

private interface RecipeImageProps : Props {
    var recipeId: String
}

private val RecipeImage = FC<RecipeImageProps> { props ->
    img {
        src = "getImageByRecipeId?$recipeIdParameterKey=${props.recipeId}"
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

            borderStyle = LineStyle.solid
            borderCollapse = BorderCollapse.collapse
            borderRadius = commonBorderRadiusAlias
        }
        tbody { // this is mandatory, see https://github.com/facebook/react/issues/5652
            tr {
                css {
                    borderColor = NamedColor.lightgrey
                    borderStyle = LineStyle.solid
                }
                th { +"Main Ingredients" }
                td { pre { +props.recipeitem.getVegetableAndMeatString() } }
            }
            tr {
                css {
                    borderColor = NamedColor.lightgrey
                    borderStyle = LineStyle.solid
                }
                th { +"Stocked Ingredients" }
                td { pre { +props.recipeitem.getSpicesAndSaucesString() } }
            }
            tr {
                css {
                    borderColor = NamedColor.lightgrey
                    borderStyle = LineStyle.solid
                }
                th { +"Cooking Instructions" }
                td { pre { +props.recipeitem.getCookingInstructionsString() } }
            }
        }
    }
}
