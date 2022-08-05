package recipes

import atomics.Ingredient
import atomics.Tag
import atomics.IngredientType
import atomics.Recipe

class BakedSweetPotato {

    companion object {
        val recipe = Recipe(
            "Baked Sweet Potato",
            listOf(
                Ingredient(IngredientType.SWEET_POTATO)
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