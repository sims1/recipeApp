package store

import atomics.Recipe
import getRecipesById

private typealias SerializableRedirectType = Map<Recipe, Int>
private typealias DeserializableRedirectType = Array<String>

fun SerializableRedirectType.serialize(): DeserializableRedirectType {
    return this.map { (key, value) -> "${key.id} $value" }
        .toTypedArray()
}

suspend fun DeserializableRedirectType.deserialize(): SerializableRedirectType {
    return this.associate {
        val items = it.split(" ")
        val recipeId = items[0]
        println("recipeId: $recipeId")
        val recipe = getRecipesById(recipeId)
        val quantity = items[1].toInt()
        recipe to quantity
    }
}