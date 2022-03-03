import kotlinx.browser.document
import react.create
import react.dom.render

// Following
// https://play.kotlinlang.org/hands-on/Building%20Web%20Applications%20with%20React%20and%20Kotlin%20JS/01_Introduction
// https://github.com/kotlin-hands-on/web-app-react-kotlin-js-gradle
// \todo study react
// \todo study html
// \todo study css

fun main() {
    val container = document.getElementById("root") ?: error("Couldn't find root container!")
    render(App.create(), container)
}