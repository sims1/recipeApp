package com.mo.recipe.app.components.index

import com.mo.recipe.app.components.common.textFontFamilyAlias
import com.mo.recipe.app.components.common.textFontSizeAlias
import com.mo.recipe.app.recipes.atomics.RecipeType
import com.mo.recipe.app.recipes.atomics.VegetableAndMeatType
import react.FC
import react.Props
import react.css.css
import react.dom.html.InputType
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.p

external interface FilterSidebarProps : Props {
    var recipeTypes: List<RecipeType>
    var onSelectedType: (RecipeType) -> Unit
    var onUnselectedType: (RecipeType) -> Unit
    var ingredients: List<VegetableAndMeatType>
    var onSelectedIngredient: (VegetableAndMeatType) -> Unit
    var onUnselectedIngredient: (VegetableAndMeatType) -> Unit
}

val FilterSidebar = FC<FilterSidebarProps> { props ->
    p {
        css {
            fontFamily = textFontFamilyAlias
            fontSize = textFontSizeAlias
        }
        +"Recipe types"
    }
    props.recipeTypes.map { recipeType ->
        label {
            css {
                fontFamily = textFontFamilyAlias
                fontSize = textFontSizeAlias
            }
            input {
                type = InputType.checkbox
                value = recipeType.value
                onChange = { event ->
                    when (event.target.checked) {
                        true -> props.onSelectedType(recipeType)
                        else -> props.onUnselectedType(recipeType)
                    }
                }
            }
            +recipeType.value
        }
        br {}
    }

    p {
        css {
            fontFamily = textFontFamilyAlias
            fontSize = textFontSizeAlias
        }
        +"Ingredients"
    }
    props.ingredients.map { ingredient ->
        label {
            css {
                fontFamily = textFontFamilyAlias
                fontSize = textFontSizeAlias
            }
            input {
                type = InputType.checkbox
                value = ingredient.getValue()
                onChange = { event ->
                    when (event.target.checked) {
                        true -> props.onSelectedIngredient(ingredient)
                        else -> props.onUnselectedIngredient(ingredient)
                    }
                }
            }
            +ingredient.getValue()
        }
        br {}
    }
}