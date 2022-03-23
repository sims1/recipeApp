package com.mo.recipe.app.components.index

import com.mo.recipe.app.components.common.recipeNameColorAlias
import com.mo.recipe.app.components.common.textFontFamilyAlias
import com.mo.recipe.app.components.common.textFontSizeAlias
import com.mo.recipe.app.recipes.atomics.RecipeType
import csstype.TextDecorationStyle.Companion.solid
import csstype.pc
import react.FC
import react.Props
import react.css.css
import react.dom.html.InputType
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label

external interface FilterSidebarProps : Props {
    var recipeTypes: List<RecipeType>
    var onSelectedType: (RecipeType) -> Unit
    var onUnselectedType: (RecipeType) -> Unit
}

val FilterSidebar = FC<FilterSidebarProps> { props ->
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
}