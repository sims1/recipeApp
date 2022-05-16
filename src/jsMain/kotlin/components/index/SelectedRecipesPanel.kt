package components.index

import atomics.Recipe
import components.common.*
import store.serialize
import csstype.*
import csstype.TextAlign.Companion.center
import react.FC
import react.Props
import react.css.css
import react.dom.html.ButtonType
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import react.router.NavigateOptions
import react.router.useNavigate
import react.useState

external interface SelectedRecipesPanelProps : Props {
    var selectedRecipes: Map<Recipe, Int>
    var onRecipeIncrement: (Recipe) -> Unit
    var onRecipeDecrement: (Recipe) -> Unit
}

val SelectedRecipesPanel = FC<SelectedRecipesPanelProps> { props ->
    var showSelectedRecipes: Boolean by useState(false)
    div {
        css {
            onMouseLeave = { showSelectedRecipes = false }
            textAlign = center
        }
        button {
            css {
                onMouseEnter = { showSelectedRecipes = true }

                fontFamily = textFontFamilyAlias
                fontSize = h2FontSizeAlias
                backgroundColor = recipeNameColorAlias
                color = NamedColor.white
                borderStyle = None.none
                borderRadius = roundBorderRadius
                height = 8.pc
                width = 8.pc
                cursor = Cursor.pointer
            }
            +"Selected"
            br { }
            +"Recipes"
        }

        if (showSelectedRecipes) {
            SelectedRecipeHoverBox {
                selectedRecipes = props.selectedRecipes
                onRecipeIncrement = props.onRecipeIncrement
                onRecipeDecrement = props.onRecipeDecrement
            }
        }
    }
}

private val SelectedRecipeHoverBox = FC<SelectedRecipesPanelProps> { props ->
    div {
        css {
            borderColor = recipeNameColorAlias
            borderRadius = commonBorderRadiusAlias
            borderStyle = LineStyle.solid
            borderWidth = 0.2.pc
            marginTop = 2.pc
            paddingBlock = 1.pc
        }

        props.selectedRecipes.map { (recipe, numOfRecipes) ->
            div {
                css {
                    textAlign = TextAlign.left
                    paddingLeft = 1.pc
                    paddingRight = 1.pc
                }
                button {
                    css {
                        fontFamily = textFontFamilyAlias
                        fontSize = textFontSizeAlias
                        backgroundColor = recipeNameColorAlias
                        color = NamedColor.white
                        borderStyle = None.none
                        borderRadius = roundBorderRadius
                    }
                    type = ButtonType.button
                    onClick = { props.onRecipeIncrement(recipe) }
                    +"+"
                }
                span {
                    css {
                        fontFamily = textFontFamilyAlias
                        fontSize = textFontSizeAlias
                    }
                    +" $numOfRecipes "
                }
                button {
                    css {
                        fontFamily = textFontFamilyAlias
                        fontSize = textFontSizeAlias
                        backgroundColor = recipeNameColorAlias
                        color = NamedColor.white
                        borderStyle = None.none
                        borderRadius = roundBorderRadius
                    }
                    type = ButtonType.button
                    onClick = { props.onRecipeDecrement(recipe) }
                    +"-"
                }
                span {
                    css {
                        fontFamily = textFontFamilyAlias
                        fontSize = textFontSizeAlias
                    }
                    +"   ${recipe.getNameString()}"
                }
                br {}
            }
        }

        val navigate = useNavigate()
        button {
            css {
                fontSize = unimportantFontSizeAlias
                backgroundColor = recipeNameColorAlias
                color = buttonFontColor
                borderStyle = None.none
                borderRadius = commonButtonBorderRadiusAlias
                paddingLeft = 1.pc
                paddingRight = 1.pc
                paddingTop = 0.5.pc
                paddingBottom = 0.5.pc
                cursor = Cursor.pointer
                marginBlock = 1.pc
            }
            type = ButtonType.button
            onClick = {
                navigate(
                    "/result",
                    object : NavigateOptions {
                        override var replace: Boolean? = true
                        override var state: Any? = props.selectedRecipes.serialize()
                    }
                )
            }
            +"Compute"
        }
    }
}