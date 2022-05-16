package pages

import atomics.Recipe
import atomics.RecipeType
import atomics.VegetableAndMeatType
import com.mo.recipe.app.components.shared.Footer
import components.shared.Header
import components.common.buttonFontColor
import components.common.commonButtonBorderRadiusAlias
import components.common.recipeNameColorAlias
import components.common.unimportantFontSizeAlias
import components.index.FilterSidebar
import components.index.RecipeTable
import components.index.SelectedRecipesPanel
import store.InMemoryRecipeStore
import csstype.*
import getRecipeList
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.css.css
import react.dom.html.ButtonType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.section
import react.router.NavigateOptions
import react.router.useNavigate
import react.useEffectOnce
import react.useState

// Following
// https://play.kotlinlang.org/hands-on/Building%20Web%20Applications%20with%20React%20and%20Kotlin%20JS/01_Introduction

private val scope = MainScope()
external interface IndexPageProps : Props {
    var recipeList: List<Recipe>
}

val IndexPage = FC<IndexPageProps> {
    var recipeListState by useState(emptyList<Recipe>())
    var selectedTypesState: Set<RecipeType> by useState(emptySet())
    var selectedIngredientsState: Set<VegetableAndMeatType> by useState(emptySet())
    var selectedRecipesState: MutableMap<Recipe, Int> by useState(mutableMapOf())

    useEffectOnce {
        scope.launch {
            recipeListState = getRecipeList()
        }
    }

    Header { }

    val navigate = useNavigate()
    button {
        css {
            fontSize = unimportantFontSizeAlias
            backgroundColor = recipeNameColorAlias
            color = buttonFontColor
            borderStyle = None.none
            borderRadius = commonButtonBorderRadiusAlias
            paddingLeft = 1.pc
            paddingRight = 1.pc
            paddingTop = 0.5.pc
            paddingBottom = 0.5.pc
            cursor = Cursor.pointer
            marginBlock = 1.pc
        }
        type = ButtonType.button
        onClick = {
            navigate(
                "/edit",
                object : NavigateOptions {
                    override var replace: Boolean? = true
                    override var state: Any? = null
                }
            )
        }
        +"Add new recipe \uD83D\uDE0A"
    }

    section {
        css {
            display = Display.grid
            gridTemplateAreas = GridTemplateAreas(
                GridArea("FilterSidebar RecipeTable SelectedRecipesPanel")
            )
        }

        div {
            css {
                gridArea = GridArea("FilterSidebar")
                textAlign = TextAlign.left
                width = 15.pc
            }
            FilterSidebar {
                recipeTypes = RecipeType.values().toList()
                onSelectedType = { selectedType -> selectedTypesState = selectedTypesState + selectedType }
                onUnselectedType = { unselectedType -> selectedTypesState = selectedTypesState - unselectedType }
                ingredients = VegetableAndMeatType.values().toList()
                onSelectedIngredient = { selectedIngredient -> selectedIngredientsState += selectedIngredient }
                onUnselectedIngredient = { unselectedIngredient -> selectedIngredientsState -= unselectedIngredient }
            }
        }

        div {
            css {
                gridArea = GridArea("RecipeTable")
                width = 45.pc
                textAlign = TextAlign.center
            }
            RecipeTable {
                recipes = recipeListState
                selectedTypes = selectedTypesState
                selectedIngredients = selectedIngredientsState
                onSelectRecipe = { recipe -> recipeIncrement(selectedRecipesState, recipe) }
            }
        }

        div {
            css {
                gridArea = GridArea("SelectedRecipesPanel")
                width = 25.pc
            }
            SelectedRecipesPanel {
                selectedRecipes = selectedRecipesState
                onRecipeIncrement = { recipe -> recipeIncrement(selectedRecipesState, recipe) }
                onRecipeDecrement = { recipe -> recipeDecrement(selectedRecipesState, recipe) }
            }
        }
    }

    Footer { }
}

private fun recipeIncrement(selectedRecipesState: MutableMap<Recipe, Int>, recipe: Recipe) {
    when (val numOfSelected = selectedRecipesState[recipe]) {
        null -> selectedRecipesState[recipe] = 1
        else -> selectedRecipesState[recipe] = numOfSelected + 1
    }
}

private fun recipeDecrement(selectedRecipesState: MutableMap<Recipe, Int>, recipe: Recipe) {
    when(val numOfSelected = selectedRecipesState[recipe]!!) {
        1 -> selectedRecipesState.remove(recipe)
        else -> selectedRecipesState[recipe] = numOfSelected - 1
    }
}