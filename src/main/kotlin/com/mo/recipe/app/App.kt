package com.mo.recipe.app

import com.mo.recipe.app.pages.ComputedResultPage
import com.mo.recipe.app.pages.IndexPage
import react.FC
import react.Props
import react.create
import react.router.Route
import react.router.Routes
import react.router.dom.BrowserRouter

val App = FC<Props> {
    BrowserRouter {
        Routes {
            Route {
                index = true
                element = IndexPage.create()
            }
            Route {
                path = "/result"
                element = ComputedResultPage.create()
            }
        }
    }
}