package com.mo.recipe.app.RecipeApp

import com.mo.recipe.app.RecipeApp.recipes.atomics.Recipe
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Article(
    var title: String,
    var headline: String,
    var content: String,
    @ManyToOne var author: User,
    var slug: String = title.toSlug(),
    var addedAt: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue var id: Long? = null)

@Entity
class User(
    var login: String,
    var firstname: String,
    var lastname: String,
    var description: String? = null,
    @Id @GeneratedValue var id: Long? = null)

@Entity
class RecipeEntity(
    var type: String,
    var name: String,
    var ingredients: String,
    var spicesAndSauces: String,
    var cookingInstructions: String,
    @Id @GeneratedValue var id: Long? = null
)

internal fun Recipe.toRecipeEntity(): RecipeEntity {
    return RecipeEntity(
        this.type.value,
        this.name,
        this.ingredients
            .map { item -> item.value }
            .joinToString("\n"),
        this.spicesAndSauces
            .map { item -> item.value }
            .joinToString("\n"),
        this.cookingInstructions.joinToString("\n")
    )
}