package com.mo.recipe.app.components.index

import ReactButton
import com.mo.recipe.app.components.common.*
import com.mo.recipe.app.recipes.atomics.Recipe
import com.mo.recipe.app.serialize
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
import react.router.dom.Link
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

                fontSize = textFontSizeAlias
                backgroundColor = recipeNameColorAlias
                color = NamedColor.white
                borderStyle = None.none
                borderRadius = 50.pc
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
        props.selectedRecipes.map { (recipe, numOfRecipes) ->
            button {
                type = ButtonType.button
                onClick = { props.onRecipeIncrement(recipe) }
                +"+"
            }
            span {
                +"${recipe.getNameString()}"
            }
            button {
                type = ButtonType.button
                onClick = { props.onRecipeDecrement(recipe) }
                +"-"
            }
            span {
                +"x $numOfRecipes"
            }
            br {}
        }

        val navigate = useNavigate()
        ReactButton {
            type = "primary"
            onPress = {
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