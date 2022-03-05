package com.mo.recipe.app.components

import csstype.TextAlign
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML

val Header = FC<Props> {
    ReactHTML.header {
        css {
            textAlign = TextAlign.center
        }
        ReactHTML.p {
            +"Ling's favorite recipes"
        }
    }
}