package atomics.ingredient

import kotlinx.serialization.Serializable

enum class IngredientType(val value: String) {
    MAJOR_INGREDIENT("MajorIngredient"),
    MINOR_INGREDIENT("MinorIngredient"),
    SEASONING_INGREDIENT("Seasoning"),
}

@Serializable
class Ingredient(val name: String, val ingredientType: IngredientType) : BaseIngredient {

    companion object {
        val CHICKEN_BREAST = Ingredient("Chicken breast", IngredientType.MAJOR_INGREDIENT)
        val CHICKEN_THIGH = Ingredient("Chicken thigh", IngredientType.MAJOR_INGREDIENT)
        val CHICKEN_WING = Ingredient("Chicken wing", IngredientType.MAJOR_INGREDIENT)
        val BACON = Ingredient("Bacon", IngredientType.MAJOR_INGREDIENT)
        val SALMON = Ingredient("Salmon", IngredientType.MAJOR_INGREDIENT)
        val STEAK = Ingredient("Steak", IngredientType.MAJOR_INGREDIENT)
        val PORK_BITS = Ingredient("Pork", IngredientType.MAJOR_INGREDIENT)
        val CANTONESE_SAUSAGE = Ingredient("Cantonese sausage(广东腊肠)", IngredientType.MAJOR_INGREDIENT)
        val EGG = Ingredient("Egg", IngredientType.MAJOR_INGREDIENT)
        val TUNA = Ingredient("Tuna", IngredientType.MAJOR_INGREDIENT)
        val BASA = Ingredient("Basa", IngredientType.MAJOR_INGREDIENT)
        val SOFT_TOFU = Ingredient("Soft", IngredientType.MAJOR_INGREDIENT)
        val MEDIUM_TOFU = Ingredient("Medium tofu", IngredientType.MAJOR_INGREDIENT)
        val FIRM_TOFU = Ingredient("Firm tofu", IngredientType.MAJOR_INGREDIENT)
        val TOFU_SHEET = Ingredient("Tofu sheet(豆腐皮)", IngredientType.MAJOR_INGREDIENT)
        val FRIED_TOFU = Ingredient("Fried tofu(油豆腐)", IngredientType.MAJOR_INGREDIENT)
        val GLUTEN = Ingredient("Gluten(面筋)", IngredientType.MAJOR_INGREDIENT)

        val SWEET_POTATO = Ingredient("Sweet potato", IngredientType.MAJOR_INGREDIENT)
        val ZUCCHINI = Ingredient("Zucchini", IngredientType.MAJOR_INGREDIENT)
        val POTATO = Ingredient("Potato", IngredientType.MAJOR_INGREDIENT)

        val GARLIC = Ingredient("Garlic", IngredientType.MINOR_INGREDIENT)
    }

    override fun getValue(): String {
        return name
    }

    fun isMajorIngredient(): Boolean {
        return ingredientType == IngredientType.MAJOR_INGREDIENT
    }

    fun isSeasoning(): Boolean {
        return ingredientType == IngredientType.SEASONING_INGREDIENT
    }
}