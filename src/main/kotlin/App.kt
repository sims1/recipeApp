import com.mo.recipe.app.recipes.atomics.RecipeType
import com.mo.recipe.app.recipes.BakedSweetPotato
import com.mo.recipe.app.recipes.ItalianZucchini
import com.mo.recipe.app.recipes.MashedPotato
import com.mo.recipe.app.components.RecipeTable
import csstype.*
import react.FC
import react.Props
import react.css.css
import react.dom.html.InputType
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.footer
import react.dom.html.ReactHTML.header
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.section

// Following
// https://play.kotlinlang.org/hands-on/Building%20Web%20Applications%20with%20React%20and%20Kotlin%20JS/01_Introduction

private val recipeList = listOf(
    BakedSweetPotato.recipe,
    ItalianZucchini.recipe,
    MashedPotato.recipe
)


val App = FC<Props> {
    //var recipeList: List<Recipe> by useState(emptyList())

    header {
        css {
            textAlign = TextAlign.center
        }
        p {
            +"Ling's favorite recipes"
        }
    }

    section {
        css {
            display = Display.flex
            flexDirection = FlexDirection.row
        }

        div {
            RecipeType.values().map { recipeTypeEnum -> recipeTypeEnum.value }
                .map { recipeType ->
                    label {
                        input {
                            type = InputType.checkbox
                            value = recipeType
                            onChange = { event -> println(event.target.checked) }
                        }
                        +recipeType
                    }
                    br {}
                }
        }

        div {
            RecipeTable {
                recipes = recipeList
            }
        }
    }

    footer {
        css {
            textAlign = TextAlign.center
        }
        p {
            +"myFooter"
        }
    }

}