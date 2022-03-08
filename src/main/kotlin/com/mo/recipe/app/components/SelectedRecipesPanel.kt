package com.mo.recipe.app.components

import ReactButton
import com.mo.recipe.app.recipes.atomics.Recipe
import react.FC
import react.Props
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.p
import react.router.NavigateOptions
import react.router.useNavigate

external interface SelectedRecipesPanelProps : Props {
    var selectedRecipes: Set<Recipe>
    var onUnselectedRecipe: (Recipe) -> Unit
}

val SelectedRecipesPanel = FC<SelectedRecipesPanelProps> { props ->

    p {
        +"Selected Recipes"
    }

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
        onPress= {
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