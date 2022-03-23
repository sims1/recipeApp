plugins {
    kotlin("js") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"

    // mandatory to resolve build issues, see
    // https://plugins.gradle.org/plugin/com.github.turansky.kfc.legacy-union
    // https://github.com/turansky/kfc-plugins
    id("com.github.turansky.kfc.legacy-union") version "4.88.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    js {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
        binaries.executable()
    }
}

dependencies {
    //React, React DOM + Wrappers (chapter 3)
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:17.0.2-pre.297-kotlin-1.6.10")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:17.0.2-pre.297-kotlin-1.6.10")
    implementation(npm("react", "17.0.2"))
    implementation(npm("react-dom", "17.0.2"))

    //Kotlin React CSS (chapter 3)
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-css:17.0.2-pre.298-kotlin-1.6.10")

    //Video Player (chapter 7)
    implementation(npm("react-youtube-lite", "1.5.0"))

    //Share Buttons (chapter 7)
    implementation(npm("react-share", "4.4.0"))

    //Coroutines & serialization (chapter 8)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    implementation(npm("react-awesome-button", "6.5.1"))

    // https://github.com/JetBrains/kotlin-wrappers/tree/master/kotlin-react-router-dom
    //implementation(npm("react-router-dom", "6.2.1"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.2.1-pre.304-kotlin-1.6.10")
}

// Heroku Deployment (chapter 9)
tasks.register("stage") {
    dependsOn("build")
}