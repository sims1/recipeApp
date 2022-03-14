package com.mo.recipe.app.components.shared

import com.mo.recipe.app.components.common.fancyH1FontFamilyAlias
import com.mo.recipe.app.components.common.h1FontSizeAlias
import csstype.TextAlign
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.header
import react.dom.html.ReactHTML.p

val Header = FC<Props> {
    header {
        css {
            textAlign = TextAlign.center
            fontSize = h1FontSizeAlias
            fontFamily = fancyH1FontFamilyAlias
        }
        p {
            +"Ling's favorite recipes"
        }
    }
}