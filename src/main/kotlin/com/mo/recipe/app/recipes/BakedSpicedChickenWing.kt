package com.mo.recipe.app.recipes

import com.mo.recipe.app.recipes.atomics.*

class BakedSpicedChickenWing {

    companion object {
        val recipe = Recipe(
            RecipeType.MEAT,
            "Baked Spiced Chicken Wing",
            listOf(
                Ingredient(VegetableAndMeatType.CHICKEN_WING)
            ),
            listOf(
                Ingredient(SpiceAndSauceType.MARINATING_SPICE_PACK, "嘟嘟 天禾 香辣鸡/新奥尔良/蜜汁叉烧")
            ),
            listOf(
                "400F 40min",
            )
        )
    }
}