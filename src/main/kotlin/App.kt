import com.mo.recipe.app.components.FilterSidebar
import com.mo.recipe.app.components.Footer
import com.mo.recipe.app.components.Header
import com.mo.recipe.app.recipes.atomics.RecipeType
import com.mo.recipe.app.recipes.BakedSweetPotato
import com.mo.recipe.app.recipes.ItalianZucchini
import com.mo.recipe.app.recipes.MashedPotato
import com.mo.recipe.app.components.RecipeTable
import csstype.*
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.section
import react.useState

// Following
// https://play.kotlinlang.org/hands-on/Building%20Web%20Applications%20with%20React%20and%20Kotlin%20JS/01_Introduction

private val recipeList = listOf(
    BakedSweetPotato.recipe,
    ItalianZucchini.recipe,
    MashedPotato.recipe
)


val App = FC<Props> {
    var selectedTypesState: Set<RecipeType> by useState(emptySet())

    Header { }

    section {
        css {
            display = Display.flex
            flexDirection = FlexDirection.row
        }

        div {
            FilterSidebar {
                recipeTypes = RecipeType.values().toList()
                onSelectedType = { selectedType ->
                    selectedTypesState += selectedType
                }
                onUnselectedType = { unselectedType ->
                    selectedTypesState -= unselectedType
                }
            }
        }

        div {
            RecipeTable {
                recipes = recipeList
                selectedTypes = selectedTypesState
            }
        }
    }

    Footer { }

}