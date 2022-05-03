package com.mo.recipe.app.components.shared

import com.mo.recipe.app.components.common.*
import com.mo.recipe.app.serialize
import csstype.Cursor
import csstype.FontWeight.Companion.bolder
import csstype.None
import csstype.TextAlign
import csstype.TextAlign.Companion.center
import csstype.pc
import react.FC
import react.Props
import react.css.css
import react.dom.html.ButtonType
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.header
import react.dom.html.ReactHTML.p
import react.router.NavigateOptions
import react.router.useNavigate

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