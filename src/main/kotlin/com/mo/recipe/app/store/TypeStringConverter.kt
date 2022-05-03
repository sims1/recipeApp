package com.mo.recipe.app.store

import com.mo.recipe.app.recipes.atomics.BasedIngredient
import com.mo.recipe.app.recipes.atomics.SpiceAndSauceType
import com.mo.recipe.app.recipes.atomics.VegetableAndMeatType

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
