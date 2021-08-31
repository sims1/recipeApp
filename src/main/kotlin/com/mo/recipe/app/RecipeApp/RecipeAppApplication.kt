package com.mo.recipe.app.RecipeApp

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RecipeAppApplication

fun main(args: Array<String>) {
	runApplication<RecipeAppApplication>(*args) {
		setBannerMode(Banner.Mode.OFF)
	}
}
