package components.common

import atomics.SpiceAndSauceType
import atomics.VegetableAndMeatType

class TypeStringConverter {

    companion object {
        fun getVegetableAndMeatType(input: String): VegetableAndMeatType? {
            return vegetableAndMeatStringToTypeMap[input]
        }

        fun getSpiceAndSauceType(input: String): SpiceAndSauceType? {
            return spiceAndSauceTypeStringToTypeMap[input]
        }

        private val vegetableAndMeatStringToTypeMap = VegetableAndMeatType.values().associateBy { it.getValue() }
        private val spiceAndSauceTypeStringToTypeMap = SpiceAndSauceType.values().associateBy { it.getValue() }
    }

}
