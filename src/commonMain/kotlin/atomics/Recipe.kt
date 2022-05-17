package atomics

import kotlinx.serialization.Serializable
@Serializable
class Recipe(
    val type: RecipeType,
    private val name: String,
    val vegetableAndMeat: List<Ingredient<VegetableAndMeatType>>,
    val spicesAndSauces: List<Ingredient<SpiceAndSauceType>>,
    private val cookingInstructions: List<String>,
    val id: String = name.filter { !it.isWhitespace() }
) {
    private fun getTypeString() = type.value
    fun getNameString() = name
    fun getVegetableAndMeatString() = vegetableAndMeat.joinToString("\n") { it.getString() }
    fun getSpicesAndSaucesString() = spicesAndSauces.joinToString("\n") { it.getString() }
    fun getCookingInstructionsString() = cookingInstructions.joinToString("\n")
    override fun toString(): String {
        return """
            {
                Type: ${getTypeString()},
                Name: ${getNameString()},
                VegetableAndMeat: ${getVegetableAndMeatString()},
                SpicesAndSauces: ${getSpicesAndSaucesString()},
                CookingInstructions: ${getCookingInstructionsString()}
            }
            """
    }

    companion object {
        const val create_path = "/create"
        const val get_all_path = "/getall"
        const val get_by_recipe_id_path = "/getbyrecipeid"
    }
}