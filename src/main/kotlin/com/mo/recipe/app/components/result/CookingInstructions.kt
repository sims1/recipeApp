package com.mo.recipe.app.components.result

import com.mo.recipe.app.recipes.atomics.Recipe
import csstype.*
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.section

external interface CookingInstructionsProps : Props {
    var recipes: List<Recipe>
}

val CookingInstructions = FC<CookingInstructionsProps> { props ->
    h1 {
        css {
            fontFamily = FontFamily.cursive
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
                        GridArea("VegetableAndMeatAndSpicesAndSauces"),
                        GridArea("CookingInstructions")
                    )

                    backgroundColor = NamedColor.moccasin

                    margin = 1.pc
                    width = 20.pc
                    padding = 2.pc
                    borderRadius = 10.px

                    whiteSpace = WhiteSpace.preWrap
                    fontFamily = FontFamily.monospace
                    fontSize = 1.2.em
                }
                div {
                    css {
                        gridArea = GridArea("RecipeName")
                        backgroundColor = NamedColor.coral
                        textAlign = TextAlign.center
                        fontFamily = FontFamily.fantasy
                        fontSize = 1.5.em

                        paddingTop = 0.5.pc
                        paddingBottom = 0.2.pc
                        borderRadius = 10.px
                        marginBottom = 0.5.pc
                    }
                    +recipe.getNameString()
                }
                div {
                    css {
                        gridArea = GridArea("VegetableAndMeatAndSpicesAndSauces")
                        backgroundColor = NamedColor.lightsalmon

                        padding = 0.2.pc
                        borderRadius = 10.px
                        marginBottom = 0.5.pc

                        gridTemplateAreas = GridTemplateAreas(GridArea("VegetableAndMeat SpicesAndSauces"))
                    }
                    div {
                        css {
                            gridArea = GridArea("VegetableAndMeat")
                        }
                        +recipe.getVegetableAndMeatString()
                    }
                    div {
                        css {
                            gridArea = GridArea("SpicesAndSauces")
                        }
                        +recipe.getSpicesAndSaucesString()
                    }
                }
                div {
                    css {
                        gridArea = GridArea("CookingInstructions")
                        backgroundColor = NamedColor.lightsalmon

                        padding = 0.2.pc
                        borderRadius = 10.px
                    }
                    +recipe.getCookingInstructionsString()
                }
            }
        }
    }
}
