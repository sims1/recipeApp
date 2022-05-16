package recipes

import atomics.*

class PotatoShoots {

    companion object {
        val recipe = Recipe(
            RecipeType.CARB,
            "Potato Shoots",
            listOf(
                Ingredient(VegetableAndMeatType.POTATO)
            ),
            listOf(
                Ingredient(SpiceAndSauceType.VEGETABLE_OIL),
                Ingredient(SpiceAndSauceType.SALT),
                Ingredient(SpiceAndSauceType.BLACK_VINEGAR)
            ),
            listOf(
                "cook!"
            )
        )
    }
}