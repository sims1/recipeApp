package recipes

import atomics.*
import atomics.ingredient.IngredientDetails
import atomics.ingredient.Ingredient
import atomics.ingredient.Seasoning

class OrleansChickenWing {

    companion object {
        val recipe = Recipe(
            "Orleans Chicken Wing",
            listOf(
                IngredientDetails(Ingredient.CHICKEN_WING)
            ),
            listOf(
                IngredientDetails(Seasoning.ORLEANS_SPICE_PACK, "天禾 新奥尔良(嘟嘟)")
            ),
            listOf(Tag.CHICKEN),
            "400F 40min",
        )
    }
}