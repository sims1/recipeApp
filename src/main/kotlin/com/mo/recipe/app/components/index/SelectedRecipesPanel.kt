package com.mo.recipe.app.components.index

import ReactButton
import com.mo.recipe.app.components.common.*
import com.mo.recipe.app.recipes.atomics.Recipe
import csstype.*
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.router.NavigateOptions
import react.router.useNavigate
import react.useState

external interface SelectedRecipesPanelProps : Props {
    var selectedRecipes: Set<Recipe>
    var onUnselectedRecipe: (Recipe) -> Unit
}

val SelectedRecipesPanel = FC<SelectedRecipesPanelProps> { props ->
    var showSelectedRecipes: Boolean by useState(false)
    button {
        css {
            onMouseEnter = { showSelectedRecipes = true }
            onMouseLeave = { showSelectedRecipes = false }

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
            onUnselectedRecipe = props.onUnselectedRecipe
        }
    }
}

private interface SelectedRecipeHoverBoxProps : Props {
    var selectedRecipes: Set<Recipe>
    var onUnselectedRecipe: (Recipe) -> Unit
}

private val SelectedRecipeHoverBox = FC<SelectedRecipeHoverBoxProps> { props ->
    div {
        props.selectedRecipes.map { recipe ->
            p {
                +recipe.getNameString()
            }
            ReactButton {
                type = "primary"
                onPress = { props.onUnselectedRecipe(recipe) }
                +"cancel"
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
                        override var state: Any? = props.selectedRecipes.map { recipe -> recipe.id }.toTypedArray()
                    }
                )
            }
            +"Compute"
        }
    }
}