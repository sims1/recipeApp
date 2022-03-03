import com.mo.recipe.app.recipes.BakedSweetPotato
import com.mo.recipe.app.recipes.ItalianZucchini
import com.mo.recipe.app.recipes.MashedPotato
import react.FC
import react.Props
import recipes.RecipeTable

// Following
// https://play.kotlinlang.org/hands-on/Building%20Web%20Applications%20with%20React%20and%20Kotlin%20JS/01_Introduction

private val recipeList = listOf(
    BakedSweetPotato.recipe,
    ItalianZucchini.recipe,
    MashedPotato.recipe
)

val App = FC<Props> {
    //var recipeList: List<Recipe> by useState(emptyList())

    RecipeTable {
        recipes = recipeList
    }

}