package atomics.ingredient


val sortedIngredientType = IngredientType.values().sortedBy { it.getValue() }

enum class IngredientType(private val value: String, val isMainIngredient: Boolean = true) : BaseIngredient {
    CHICKEN_WING("Chicken wing"),
    STEAK("Steak"),

    POTATO("Potato"),
    SWEET_POTATO("Sweet potato"),
    ZUCCHINI("Zucchini"),

    GARLIC("Garlic", isMainIngredient = false),
    ;

    override fun getValue() = value
}
