package components.index

import atomics.Tag
import atomics.IngredientType
import components.common.commonBorderRadiusAlias
import components.common.recipeNameColorAlias
import components.common.textFontFamilyAlias
import components.common.textFontSizeAlias
import csstype.LineStyle
import csstype.pc
import react.FC
import react.Props
import react.css.css
import react.dom.html.InputType
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.p

external interface FilterSidebarProps : Props {
    var recipeTypes: List<Tag>
    var onSelectType: (Tag) -> Unit
    var onUnselectType: (Tag) -> Unit
    var ingredients: List<IngredientType>
    var onSelectIngredient: (IngredientType) -> Unit
    var onUnselectIngredient: (IngredientType) -> Unit
}

val FilterSidebar = FC<FilterSidebarProps> { props ->
    div {
        css {
            borderStyle = LineStyle.solid
            borderColor = recipeNameColorAlias
            borderRadius = commonBorderRadiusAlias
            paddingLeft = 1.pc
            paddingBottom = 1.pc
        }
        p {
            css {
                fontFamily = textFontFamilyAlias
                fontSize = textFontSizeAlias
            }
            +"Tags"
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
                            true -> props.onSelectType(recipeType)
                            else -> props.onUnselectType(recipeType)
                        }
                    }
                }
                +recipeType.value
            }
            br {}
        }
    }

    div {
        css {
            borderStyle = LineStyle.solid
            borderColor = recipeNameColorAlias
            borderRadius = commonBorderRadiusAlias
            paddingLeft = 1.pc
            paddingBottom = 1.pc
            marginTop = 1.pc
        }
        p {
            css {
                fontFamily = textFontFamilyAlias
                fontSize = textFontSizeAlias
            }
            +"Main Ingredients"
        }
        props.ingredients
            .filter { ingredientType -> ingredientType.isMainIngredient }
            .map { ingredient ->
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
                            true -> props.onSelectIngredient(ingredient)
                            else -> props.onUnselectIngredient(ingredient)
                        }
                    }
                }
                +ingredient.getValue()
            }
            br {}
        }
    }
}