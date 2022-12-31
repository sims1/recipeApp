package components.common

import atomics.ingredient.SpiceAndSauceType
import atomics.ingredient.IngredientType

class TypeStringConverter {

    companion object {
        fun getVegetableAndMeatType(input: String): IngredientType {
            return IngredientType(input)
        }

        fun getSpiceAndSauceType(input: String): SpiceAndSauceType? {
            return spiceAndSauceTypeStringToTypeMap[input]
        }

        private val spiceAndSauceTypeStringToTypeMap = SpiceAndSauceType.values().associateBy { it.getValue() }
    }

}
