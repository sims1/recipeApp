package atomics.ingredient


val sortedIngredientType = IngredientType.values().sortedBy { it.getValue() }

enum class IngredientType(private val value: String, val isMainIngredient: Boolean = true) : BaseIngredient {

    CHICKEN_BREAST("Chicken breast"),
    CHICKEN_THIGH("Chicken thigh"),
    CHICKEN_WING("Chicken wing"),
    Beef("Beef"),
    BACON("Bacon"),
    STEAK("Steak"),
    PORK_BITS("Pork bits"),

    POTATO("Potato"),
    SWEET_POTATO("Sweet potato"),
    ZUCCHINI("Zucchini"),
    EGGPLANT("Eggplant"),
    ENOKI("Enoki"),

    PICKLE_MUSTARD("Pickle mustard (酸芥菜)"),
    PICKLE_CABBAGE("Pickle cabbage (东北酸白菜)"),
    KIMCHI("Kimchi (韩国泡菜)"),
    LONG_CHILI("Long chili"),
    GARLIC("Garlic", isMainIngredient = false),
    SMALL_RED_CHILI("Small red chili (小米辣)", isMainIngredient = false),

    ;

    override fun getValue() = value
}