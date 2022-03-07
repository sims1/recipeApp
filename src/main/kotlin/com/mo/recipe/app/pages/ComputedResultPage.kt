package com.mo.recipe.app.pages

import com.mo.recipe.app.components.Footer
import com.mo.recipe.app.components.Header
import react.FC
import react.Props
import react.dom.html.ReactHTML.p

val ComputedResultPage = FC<Props> {
    Header { }

    p { +"hey" }

    Footer { }
}
