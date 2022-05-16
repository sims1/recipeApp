package recipes

import atomics.*

class OrleansChickenWing {

    companion object {
        val recipe = Recipe(
            RecipeType.MEAT,
            "Orleans Chicken Wing",
            listOf(
                Ingredient(VegetableAndMeatType.CHICKEN_WING)
            ),
            listOf(
                Ingredient(SpiceAndSauceType.ORLEANS_SPICE_PACK, "天禾 新奥尔良(嘟嘟)")
            ),
            listOf(
                "400F 40min",
            )
        )
    }
}