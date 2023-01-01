package recipes

import atomics.*
import atomics.ingredient.IngredientDetails
import atomics.ingredient.IngredientType
import atomics.ingredient.SpiceAndSauceType

class OrleansChickenWing {

    companion object {
        val recipe = Recipe(
            "Orleans Chicken Wing",
            listOf(
                IngredientDetails(IngredientType.CHICKEN_WING)
            ),
            listOf(
                IngredientDetails(SpiceAndSauceType.ORLEANS_SPICE_PACK, "天禾 新奥尔良(嘟嘟)")
            ),
            listOf(Tag.CHICKEN),
            "400F 40min",
        )
    }
}