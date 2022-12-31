package atomics.ingredient

import kotlinx.serialization.Serializable

@Serializable
class IngredientType(val name: String, val isMainIngredient: Boolean = true) : BaseIngredient {

    companion object {
        val CHICKEN_BREAST = IngredientType("Chicken breast")
        val CHICKEN_THIGH = IngredientType("Chicken thigh")
        val CHICKEN_WING = IngredientType("Chicken wing")
        val BACON = IngredientType("Bacon")
        val SALMON = IngredientType("Salmon")
        val STEAK = IngredientType("Steak")
        val PORK_BITS = IngredientType("Pork")
        val CANTONESE_SAUSAGE = IngredientType("Cantonese sausage(广东腊肠)")
        val EGG = IngredientType("Egg")
        val TUNA = IngredientType("Tuna")
        val BASA = IngredientType("Basa")
        val SOFT_TOFU = IngredientType("Soft")
        val MEDIUM_TOFU = IngredientType("Medium tofu")
        val FIRM_TOFU = IngredientType("Firm tofu")
        val TOFU_SHEET = IngredientType("Tofu sheet(豆腐皮)", false)
        val FRIED_TOFU = IngredientType("Fried tofu(油豆腐)", false)
        val GLUTEN = IngredientType("Gluten(面筋)", false)

        val SWEET_POTATO = IngredientType("Sweet potato")
        val ZUCCHINI = IngredientType("Zucchini")
        val POTATO = IngredientType("Potato")

        val GARLIC = IngredientType("Garlic", isMainIngredient = false)
    }

    override fun getValue(): String {
        return name
    }

/*


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
*/
}