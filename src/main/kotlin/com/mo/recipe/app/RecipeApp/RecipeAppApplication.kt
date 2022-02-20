package com.mo.recipe.app.RecipeApp

import com.mo.recipe.app.RecipeApp.recipes.ItalianZucchini
import com.mo.recipe.app.RecipeApp.recipes.MashedPotato
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class RecipeAppApplication {

	private val recipes = listOf(
		ItalianZucchini.recipe,
		MashedPotato.recipe
	)

	@Bean
	fun databaseInitializer(
		userRepository: UserRepository,
		articleRepository: ArticleRepository,
		recipeRepository: RecipeRepository
	) = ApplicationRunner {

		recipes
			.map { recipe -> recipe.toRecipeEntity() }
			.forEach{ recipeEntity -> recipeRepository.save(recipeEntity) }

		val smaldini = userRepository.save(User("smaldini", "Stéphane", "Maldini"))
		articleRepository.save(Article(
			title = "Reactor Bismuth is out",
			headline = "Lorem ipsum",
			content = "dolor sit amet",
			author = smaldini
		))
		articleRepository.save(Article(
			title = "Reactor Aluminium has landed",
			headline = "Lorem ipsum",
			content = "dolor sit amet",
			author = smaldini
		))
	}
}

fun main(args: Array<String>) {
	runApplication<RecipeAppApplication>(*args) {
		setBannerMode(Banner.Mode.OFF)
	}
}
