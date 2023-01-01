package recipes

import atomics.ingredient.IngredientDetails
import atomics.Tag
import atomics.ingredient.Ingredient
import atomics.Recipe

class BakedSweetPotato {

    companion object {
        val recipe = Recipe(
            "Baked Sweet Potato",
            listOf(
                IngredientDetails(Ingredient.SWEET_POTATO)
            ),
            listOf(),
            listOf(Tag.CARB),
            "using fork to poke holes on sweet potatoes\n" +
            "put into cold oven\n" +
            "425F 75min\n" +
            "turn off oven, leave it in oven for another 30min\n"
        )
    }
}