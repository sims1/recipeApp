package com.mo.recipe.app.components.result

import com.mo.recipe.app.recipes.atomics.Recipe
import csstype.NamedColor
import csstype.TextAlign
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.p

external interface CookingInstructionsProps : Props {
    var recipes: List<Recipe>
}

val CookingInstructions = FC<CookingInstructionsProps> { props ->
    p { +"Cooking Instructions" }
    props.recipes.map { recipe ->
        ReactHTML.div {
            css {
                backgroundColor = NamedColor.red
                textAlign = TextAlign.center
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
}
