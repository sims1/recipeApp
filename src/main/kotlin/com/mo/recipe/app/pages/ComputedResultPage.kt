package com.mo.recipe.app.pages

import com.mo.recipe.app.components.Footer
import com.mo.recipe.app.components.Header
import com.mo.recipe.app.store.InMemoryRecipeStore
import csstype.NamedColor
import csstype.TextAlign.Companion.center
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.router.useLocation

val ComputedResultPage = FC<Props> {
    Header { }

    val location = useLocation()
    val selectedRecipeIds = location.state as Array<String>
    val selectedRecipes = selectedRecipeIds.mapNotNull { recipeId -> InMemoryRecipeStore.get(recipeId) }

    p { +"Shopping List" }
    selectedRecipes
        .flatMap { recipe -> recipe.vegetableAndMeat }
        .groupBy { vegetableAndMeat -> vegetableAndMeat.type }
        .map { (vegetableAndMeatType, recipes) ->
            vegetableAndMeatType to recipes.sumOf { ingredient -> ingredient.quantity }
        }
        .map { (vegetableAndMeatType, quantity) ->
            p { +"${vegetableAndMeatType.value} x $quantity" }
        }

    br { }

    selectedRecipes
        .flatMap { recipe -> recipe.spicesAndSauces }
        .map { spiceAndSauceType ->
            p { +spiceAndSauceType.type.value }
        }

    p { +"Cooking Instructions" }
    selectedRecipes.map { recipe ->
        div {
            css {
                backgroundColor = NamedColor.red
                textAlign = center
            }
            +recipe.getNameString()
            br { }
            +recipe.getVegetableAndMeatString()
            br { }
            +recipe.getSpicesAndSaucesString()
            br { }
            +recipe.getCookingInstructionsString()
            br { }
        }
    }

    Footer { }
}
