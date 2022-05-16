package com.mo.recipe.app.components.shared

import components.common.unimportantColorAlias
import csstype.TextAlign.Companion.center
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.footer
import react.dom.html.ReactHTML.p

val Footer = FC<Props> {
    footer {
        css {
            textAlign = center
            color = unimportantColorAlias
        }
        p {
            +"V2.0"
        }
    }
}