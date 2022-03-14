package com.mo.recipe.app.components.shared

import csstype.TextAlign
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.footer
import react.dom.html.ReactHTML.p

val Footer = FC<Props> {
    footer {
        css {
            textAlign = TextAlign.center
        }
        p {
            +"V1.0"
        }
    }
}