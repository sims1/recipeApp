package com.mo.recipe.app.components.shared

import com.mo.recipe.app.components.common.fancyH1FontFamilyAlias
import com.mo.recipe.app.components.common.h1FontSizeAlias
import com.mo.recipe.app.components.common.recipeDetailsColorAlias
import com.mo.recipe.app.components.common.recipeNameColorAlias
import csstype.FontWeight.Companion.bolder
import csstype.TextAlign
import csstype.TextAlign.Companion.center
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML
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