package atomics.ingredient

import kotlinx.serialization.Serializable

@Serializable
class Ingredient(val name: String, val isMainIngredient: Boolean = true) : BaseIngredient {

    companion object {
        val CHICKEN_BREAST = Ingredient("Chicken breast")
        val CHICKEN_THIGH = Ingredient("Chicken thigh")
        val CHICKEN_WING = Ingredient("Chicken wing")
        val BACON = Ingredient("Bacon")
        val SALMON = Ingredient("Salmon")
        val STEAK = Ingredient("Steak")
        val PORK_BITS = Ingredient("Pork")
        val CANTONESE_SAUSAGE = Ingredient("Cantonese sausage(广东腊肠)")
        val EGG = Ingredient("Egg")
        val TUNA = Ingredient("Tuna")
        val BASA = Ingredient("Basa")
        val SOFT_TOFU = Ingredient("Soft")
        val MEDIUM_TOFU = Ingredient("Medium tofu")
        val FIRM_TOFU = Ingredient("Firm tofu")
        val TOFU_SHEET = Ingredient("Tofu sheet(豆腐皮)", false)
        val FRIED_TOFU = Ingredient("Fried tofu(油豆腐)", false)
        val GLUTEN = Ingredient("Gluten(面筋)", false)

        val SWEET_POTATO = Ingredient("Sweet potato")
        val ZUCCHINI = Ingredient("Zucchini")
        val POTATO = Ingredient("Potato")

        val GARLIC = Ingredient("Garlic", isMainIngredient = false)
    }

    override fun getValue(): String {
        return name
    }
}