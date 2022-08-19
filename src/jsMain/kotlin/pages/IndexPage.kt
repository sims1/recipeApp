package pages

import atomics.Recipe
import atomics.Tag
import atomics.ingredient.IngredientType
import components.shared.Footer
import components.shared.Header
import components.common.buttonFontColor
import components.common.commonButtonBorderRadiusAlias
import components.common.recipeNameColorAlias
import components.common.unimportantFontSizeAlias
import components.index.FilterSidebar
import components.index.RecipeTable
import components.index.SelectedRecipesPanel
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

private typealias RecipeToIntMap = Map<Recipe, Int>

val IndexPage = FC<Props> {
    var recipeListState by useState(emptyList<Recipe>())
    var selectedTagsState: Set<Tag> by useState(emptySet())
    var selectedIngredientsState: Set<IngredientType> by useState(emptySet())
    var selectedRecipesState: RecipeToIntMap by useState(emptyMap())

    useEffectOnce {
        scope.launch {
            recipeListState = getRecipeList()
        }
    }

    Header { }

    AddRecipeButton { }

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
                recipeTypes = Tag.values().toList()
                onSelectType = { selectedType -> selectedTagsState += selectedType }
                onUnselectType = { unselectedType -> selectedTagsState -= unselectedType }
                ingredients = IngredientType.values().toList()
                onSelectIngredient = { selectedIngredient -> selectedIngredientsState += selectedIngredient }
                onUnselectIngredient = { unselectedIngredient -> selectedIngredientsState -= unselectedIngredient }
            }
        }

        div {
            css {
                gridArea = GridArea("RecipeTable")
                width = 45.pc
                textAlign = TextAlign.center
            }
            RecipeTable {
                allRecipes = recipeListState
                selectedTags = selectedTagsState
                selectedIngredients = selectedIngredientsState
                onSelectRecipe = { recipe -> selectedRecipesState = selectedRecipesState.increment(recipe) }
            }
        }

        div {
            css {
                gridArea = GridArea("SelectedRecipesPanel")
                width = 25.pc
            }
            SelectedRecipesPanel {
                selectedRecipes = selectedRecipesState
                onRecipeIncrement = { recipe -> selectedRecipesState = selectedRecipesState.increment(recipe) }
                onRecipeDecrement = { recipe -> selectedRecipesState = selectedRecipesState.decrement(recipe) }
            }
        }
    }

    Footer { }
}

val AddRecipeButton = FC<Props> {
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
                    override var replace: Boolean? = false
                    override var state: Any? = null
                }
            )
        }
        +"Add new recipe \uD83D\uDE0A"
    }
}

private fun RecipeToIntMap.increment(recipe: Recipe): RecipeToIntMap {
    val result = this.toMutableMap()
    when (val numOfSelected = this[recipe]) {
        null -> result[recipe] = 1
        else -> result[recipe] = numOfSelected + 1
    }
    return result
}

private fun RecipeToIntMap.decrement(recipe: Recipe): RecipeToIntMap {
    val result = this.toMutableMap()
    when(val numOfSelected = this[recipe]!!) {
        1 -> result.remove(recipe)
        else -> result[recipe] = numOfSelected - 1
    }
    return result
}