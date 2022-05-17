package pages

import atomics.Recipe
import components.result.CookingDetails
import com.mo.recipe.app.components.shared.Footer
import components.shared.Header
import components.result.ShoppingListTable
import store.deserialize
import csstype.Auto
import csstype.pc
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.*
import react.css.css
import react.dom.html.ReactHTML.body
import react.dom.html.ReactHTML.div
import react.router.useLocation

private val scope = MainScope()

val ComputedResultPage = FC<Props> {
    var selectedRecipes: Map<Recipe, Int> by useState(emptyMap())

    val location = useLocation()
    @Suppress("UNCHECKED_CAST")
    val selectedRecipeIds = location.state as Array<String>

    useEffectOnce {
        scope.launch {
            println("useEffectOnce")
            selectedRecipes = selectedRecipeIds.deserialize()
            println(selectedRecipes)
        }
    }
    println("before Header")
    println(selectedRecipes)

    Header { }

    body {
        div {
            css {
                margin = Auto.auto
                width = 60.pc
            }
            ShoppingListTable {
                recipes = selectedRecipes
            }

            CookingDetails {
                recipes = selectedRecipes
            }

        }
    }

    Footer { }
}
