package components.common

import atomics.ingredient.SpiceAndSauceType
import atomics.ingredient.Ingredient

class TypeStringConverter {

    companion object {
        fun getVegetableAndMeatType(input: String): Ingredient {
            return Ingredient(input)
        }

        fun getSpiceAndSauceType(input: String): SpiceAndSauceType {
            return SpiceAndSauceType(input)
        }
    }

}
