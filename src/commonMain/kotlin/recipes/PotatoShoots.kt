package recipes

import atomics.*

class PotatoShoots {

    companion object {
        val recipe = Recipe(
            "Potato Shoots",
            listOf(
                Ingredient(IngredientType.POTATO)
            ),
            listOf(
                Ingredient(SpiceAndSauceType.VEGETABLE_OIL),
                Ingredient(SpiceAndSauceType.SALT),
                Ingredient(SpiceAndSauceType.BLACK_VINEGAR)
            ),
            listOf(Tag.CARB, Tag.VEGETABLE),
            "cook!"
        )
    }
}