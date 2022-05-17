import pages.IndexPage
import react.*
import kotlinx.coroutines.*
import pages.ComputedResultPage
import pages.EditRecipePage
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.router.Route
import react.router.Routes
import react.router.dom.BrowserRouter

private val scope = MainScope()

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
            Route {
                path = "/edit"
                element = EditRecipePage.create()
            }
        }
    }
}
