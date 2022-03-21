package com.mo.recipe.app

import com.mo.recipe.app.recipes.atomics.Recipe
import com.mo.recipe.app.store.InMemoryRecipeStore

private typealias SerializableRedirectType = Map<Recipe, Int>
private typealias DeserializableRedirectType = Array<String>

fun SerializableRedirectType.serialize(): DeserializableRedirectType {
    return this.map { (key, value) -> "${key.id} $value" }
        .toTypedArray()
}

fun DeserializableRedirectType.deserialize(): SerializableRedirectType {
    return this.associate {
        val items = it.split(" ")
        val recipeId = items[0]
        val recipe = InMemoryRecipeStore.get(recipeId)
        val quantity = items[1].toInt()
        recipe to quantity
    }
}