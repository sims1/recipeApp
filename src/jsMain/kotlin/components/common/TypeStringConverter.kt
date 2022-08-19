package components.common

import atomics.ingredient.SpiceAndSauceType
import atomics.ingredient.IngredientType

class TypeStringConverter {

    companion object {
        fun getVegetableAndMeatType(input: String): IngredientType? {
            return vegetableAndMeatStringToTypeMap[input]
        }

        fun getSpiceAndSauceType(input: String): SpiceAndSauceType? {
            return spiceAndSauceTypeStringToTypeMap[input]
        }

        private val vegetableAndMeatStringToTypeMap = IngredientType.values().associateBy { it.getValue() }
        private val spiceAndSauceTypeStringToTypeMap = SpiceAndSauceType.values().associateBy { it.getValue() }
    }

}
