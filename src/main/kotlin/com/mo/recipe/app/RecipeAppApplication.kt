package com.mo.recipe.app

import com.mo.recipe.app.recipes.BakedSweetPotato
import com.mo.recipe.app.recipes.ItalianZucchini
import com.mo.recipe.app.recipes.MashedPotato
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

// Followed
// https://spring.io/guides/tutorials/spring-boot-kotlin/

@SpringBootApplication
class RecipeAppApplication {

	private val recipes = listOf(
		ItalianZucchini.recipe,
		MashedPotato.recipe,
		BakedSweetPotato.recipe,
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
		articleRepository.save(
			Article(
			title = "Reactor Bismuth is out",
			headline = "Lorem ipsum",
			content = "dolor sit amet",
			author = smaldini
		)
		)
		articleRepository.save(
			Article(
			title = "Reactor Aluminium has landed",
			headline = "Lorem ipsum",
			content = "dolor sit amet",
			author = smaldini
		)
		)
	}
}

fun main(args: Array<String>) {
	runApplication<RecipeAppApplication>(*args) {
		setBannerMode(Banner.Mode.OFF)
	}
}
