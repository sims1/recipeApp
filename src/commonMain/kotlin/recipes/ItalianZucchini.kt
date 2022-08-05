package recipes

import atomics.*

class ItalianZucchini {

    companion object {
        val recipe = Recipe(
            "Italian Zucchini",
            listOf(
                Ingredient(IngredientType.ZUCCHINI)
            ),
            listOf(
                Ingredient(SpiceAndSauceType.ITALIAN_HERB_SPICE_BLEND),
                Ingredient(SpiceAndSauceType.SALT),
                Ingredient(SpiceAndSauceType.PEPPER)
            ),
            listOf(Tag.VEGETABLE),
            listOf("450F 12min")
        )
    }
}