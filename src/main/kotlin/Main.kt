import kotlinx.browser.document
import react.*
import react.css.css
import react.dom.render
import csstype.Position
import csstype.px
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.img
import kotlinx.serialization.Serializable

// Following
// https://play.kotlinlang.org/hands-on/Building%20Web%20Applications%20with%20React%20and%20Kotlin%20JS/01_Introduction
// https://github.com/kotlin-hands-on/web-app-react-kotlin-js-gradle



fun main() {
    val container = document.getElementById("root") ?: error("Couldn't find root container!")

    render(Fragment.create {
        h1 {
            +"Hello, React+Kotlin/JS!"
        }
    }, container)
}