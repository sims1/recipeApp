package components.shared

import components.common.fancyH1FontFamilyAlias
import components.common.h1FontSizeAlias
import components.common.recipeNameColorAlias
import csstype.FontWeight.Companion.bolder
import csstype.TextAlign.Companion.center
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.header
import react.dom.html.ReactHTML.p

val Header = FC<Props> {
    header {
        css {
            textAlign = center
            fontSize = h1FontSizeAlias
            fontFamily = fancyH1FontFamilyAlias
            fontWeight = bolder
            color = recipeNameColorAlias
        }
        p {
            +"Ling's favourite recipes"
        }
    }
}