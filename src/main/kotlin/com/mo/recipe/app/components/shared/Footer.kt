package com.mo.recipe.app.components.shared

import csstype.TextAlign
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML

val Footer = FC<Props> {
    ReactHTML.footer {
        css {
            textAlign = TextAlign.center
        }
        ReactHTML.p {
            +"myFooter"
        }
    }
}