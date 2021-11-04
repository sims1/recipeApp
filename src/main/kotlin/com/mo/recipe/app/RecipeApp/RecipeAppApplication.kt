package com.mo.recipe.app.RecipeApp

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
class RecipeAppApplication {

	@Bean
	fun databaseInitializer(
		userRepository: UserRepository,
		articleRepository: ArticleRepository
	) = ApplicationRunner {

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
