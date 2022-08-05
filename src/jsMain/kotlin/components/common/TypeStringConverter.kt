package components.common

import atomics.SpiceAndSauceType
import atomics.IngredientType

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
