package com.mo.recipe.app.components.result

import com.mo.recipe.app.components.common.*
import com.mo.recipe.app.recipes.atomics.Recipe
import csstype.*
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.section

external interface CookingDetailsProps : Props {
    var recipes: List<Recipe>
}

val CookingDetails = FC<CookingDetailsProps> { props ->
    div {
        css {
            fontFamily = fancyHeaderFontFamilyAlias
            fontSize = headerFontSizeAlias
            fontWeight = headerFontWeightAlias
        }
        +"Cooking Instructions"
    }

    section {
        css {
            display = Display.flex
            flexWrap = FlexWrap.wrap
        }

        props.recipes.map { recipe ->
            div {
                css {
                    display = Display.grid
                    gridTemplateAreas = GridTemplateAreas(
                        GridArea("RecipeName"),
                        GridArea("Ingredients"),
                        GridArea("CookingInstructions")
                    )

                    backgroundColor = NamedColor.moccasin

                    margin = 1.pc
                    width = 24.pc
                    padding = 2.pc
                    borderRadius = commonBorderRadiusAlias

                    whiteSpace = WhiteSpace.preWrap
                    fontFamily = recipeFontFamilyAlias
                    fontSize = recipeFontSizeAlias
                }
                RecipeName {
                    gridAreaName = "RecipeName"
                    recipeName = recipe.getNameString()
                }
                Ingredients {
                    gridAreaName = "Ingredients"
                    vegetableAndMeat = recipe.getVegetableAndMeatString()
                    spicesAndSauces = recipe.getSpicesAndSaucesString()
                }
                CookingInstructions {
                    gridAreaName = "CookingInstructions"
                    cookingInstructions = recipe.getCookingInstructionsString()
                }
            }
        }
    }
}

private interface RecipeNameProps : Props {
    var gridAreaName: String
    var recipeName: String
}

private val RecipeName = FC<RecipeNameProps> { props ->
    div {
        css {
            gridArea = GridArea(props.gridAreaName)
            backgroundColor = NamedColor.coral
            textAlign = TextAlign.center
            fontFamily = FontFamily.fantasy
            fontSize = 1.5.em

            paddingTop = 0.5.pc
            paddingBottom = 0.2.pc
            borderRadius = commonBorderRadiusAlias
            marginBottom = 0.5.pc
        }
        +props.recipeName
    }
}

private interface IngredientsProps : Props {
    var gridAreaName: String
    var vegetableAndMeat: String
    var spicesAndSauces: String
}

private val Ingredients = FC<IngredientsProps> { props ->
    div {
        css {
            gridArea = GridArea(props.gridAreaName)
            backgroundColor = NamedColor.lightsalmon

            padding = 0.2.pc
            borderRadius = commonBorderRadiusAlias
            marginBottom = 0.5.pc

            gridTemplateAreas = GridTemplateAreas(GridArea("VegetableAndMeat SpicesAndSauces"))
        }
        div {
            css {
                gridArea = GridArea("VegetableAndMeat")
            }
            +props.vegetableAndMeat
        }
        div {
            css {
                gridArea = GridArea("SpicesAndSauces")
            }
            +props.spicesAndSauces
        }
    }
}

private interface CookingInstructionsProps : Props {
    var gridAreaName: String
    var cookingInstructions: String
}

private val CookingInstructions = FC<CookingInstructionsProps> { props ->
    div {
        css {
            gridArea = GridArea(props.gridAreaName)
            backgroundColor = NamedColor.lightsalmon

            padding = 0.2.pc
            borderRadius = commonBorderRadiusAlias
        }
        +props.cookingInstructions
    }
}
