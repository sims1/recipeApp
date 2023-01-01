package components.common

import atomics.ingredient.Seasoning
import atomics.ingredient.Ingredient

class TypeStringConverter {

    companion object {
        fun getVegetableAndMeatType(input: String): Ingredient {
            return Ingredient(input)
        }

        fun getSeasoningType(input: String): Seasoning {
            return Seasoning(input)
        }
    }

}
