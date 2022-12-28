package atomics.ingredient


val sortedIngredientType = IngredientType.values().sortedBy { it.getValue() }

enum class IngredientType(private val value: String, val isMainIngredient: Boolean = true) : BaseIngredient {

    CHICKEN_BREAST("Chicken breast"),
    CHICKEN_THIGH("Chicken thigh"),
    CHICKEN_WING("Chicken wing"),
    Beef("Beef"),
    BACON("Bacon"),
    SALMON("Salmon"),
    STEAK("Steak"),
    PORK_BITS("Pork bits"),
    CANTONESE_SAUSAGE("Cantonese sausage(广东腊肠)"),
    EGG("Egg"),
    TUNA("Tuna"),
    BASA("Basa"),
    SOFT_TOFU("Soft tofu"),
    MEDIUM_TOFU("Medium tofu"),
    FIRM_TOFU("Firm tofu"),
    TOFU_SHEET("Tofu sheet(豆腐皮)"),
    FRIED_TOFU("Fried tofu(油豆腐)"),
    GLUTEN("Gluten(面筋)"),

    POTATO("Potato"),
    SWEET_POTATO("Sweet potato"),
    ZUCCHINI("Zucchini"),
    EGGPLANT("Eggplant"),
    ENOKI("Enoki"),
    CARROT("Carrot"),
    ONION("Onion"),
    KERNEL_CORN("Kernel corn(玉米粒)"),
    TASTY_MUSHROOM("Tasty mushroom(香菇)"),
    CAULIFLOWER("Cauliflower"),
    SHALLOT("Shallot"),
    GREEN_PEAS("Green Peas"),
    CELERY("Celery"),
    LEMON("Lemon"),

    PICKLE_MUSTARD("Pickle mustard (酸芥菜)"),
    PICKLE_CABBAGE("Pickle cabbage (东北酸白菜)"),
    KIMCHI("Kimchi (韩国泡菜)"),
    LONG_CHILI("Long chili"),

    MILK("Milk"),
    MOZZARELLA("Mozzarella"),
    COCONUT_MILK("Coconut milk"),

    GINGER("Ginger"),
    BASIL("Basil"),
    CORIANDER("Coriander(香菜)"),
    GREEN_ONION("Green Onion"),
    GARLIC("Garlic", isMainIngredient = false),
    SMALL_RED_CHILI("Small red chili (小米辣)", isMainIngredient = false),
    CHICKEN_BROTH("Chicken broth"),

    ALL_PURPOSE_FLOUR("All purpose flour"),
    RICE("Rice"),
    RICE_PAPER("Rice Paper(越南米纸)"),
    CIABATTA_BREAD("Ciabatta bread"),
    MACARONI("Macaroni"),
    PASTA("Pasta"),

    ;

    override fun getValue() = value
}