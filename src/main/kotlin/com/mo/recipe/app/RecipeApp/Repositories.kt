package com.mo.recipe.app.RecipeApp

import com.mo.recipe.app.RecipeApp.recipes.atomics.Recipe
import org.springframework.data.repository.CrudRepository

interface ArticleRepository : CrudRepository<Article, Long> {
    fun findBySlug(slug: String): Article?
    fun findAllByOrderByAddedAtDesc(): Iterable<Article>
}

interface UserRepository : CrudRepository<User, Long> {
    fun findByLogin(login: String): User?
}

interface RecipeRepository : CrudRepository<RecipeEntity, Long> {

}