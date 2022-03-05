package com.mo.recipe.app.components

import com.mo.recipe.app.recipes.atomics.RecipeType
import react.FC
import react.Props
import react.dom.html.InputType
import react.dom.html.ReactHTML

val FilterSidebar = FC<Props> {
    RecipeType.values().map { recipeTypeEnum -> recipeTypeEnum.value }
        .map { recipeType ->
            ReactHTML.label {
                ReactHTML.input {
                    type = InputType.checkbox
                    value = recipeType
                    onChange = { event -> println(event.target.checked) }
                }
                +recipeType
            }
            ReactHTML.br {}
        }
}