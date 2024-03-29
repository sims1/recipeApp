import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

val ktorVersion = "2.0.3"
val logbackVersion = "1.2.11"
val log4jVersion = "2.18.0"
val reactVersion = "17.0.2-pre.299-kotlin-1.6.10" // "18.0.0-pre.331-kotlin-1.6.20"
val reactRouterDomVersion = "6.2.1-pre.304-kotlin-1.6.10" //"6.3.0-pre.357"
val kmongoVersion = "4.6.1"
val redisClientVersion = "0.7.1"
val kotlinCoroutinesVersion = "1.6.4"

plugins {
    kotlin("multiplatform") version "1.6.21"
    application //to run JVM part
    kotlin("plugin.serialization") version "1.6.21"

    // mandatory to resolve build issues, like use word default in generated js for production mode, see
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
    jvm {
        withJava()
    }
    js {
        browser {
            binaries.executable()
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-core:$ktorVersion")
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
                implementation("io.ktor:ktor-server-compression:$ktorVersion")
                implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-server-cors:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-server-sessions:$ktorVersion")
                implementation("io.ktor:ktor-server-auth:$ktorVersion")
                implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")

                // https://ktor.io/docs/logging.html#configure-logger
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
                implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
                implementation("org.apache.logging.log4j:log4j-slf4j-impl:$log4jVersion")

                implementation("org.litote.kmongo:kmongo-coroutine-serialization:$kmongoVersion")

                //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
                implementation("io.github.crackthecodeabhi:kreds:$redisClientVersion")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-js:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-client-auth:$ktorVersion")

                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$reactVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:$reactVersion")

                //Kotlin React CSS (chapter 3)
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-css:$reactVersion")

                // https://github.com/JetBrains/kotlin-wrappers/tree/master/kotlin-react-router-dom
                //implementation(npm("react-router-dom", "6.2.1"))
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:$reactRouterDomVersion")
            }
        }
    }
}

application {
    mainClass.set("ServerKt")
}

// include JS artifacts in any JAR we generate
tasks.getByName<Jar>("jvmJar") {
    val taskName = if (project.hasProperty("isProduction")
        || project.gradle.startParameter.taskNames.contains("installDist")
    ) {
        "jsBrowserProductionWebpack"
    } else {
        "jsBrowserDevelopmentWebpack"
    }
    val webpackTask = tasks.getByName<KotlinWebpack>(taskName)
    dependsOn(webpackTask) // make sure JS gets compiled first
    from(File(webpackTask.destinationDirectory, webpackTask.outputFileName)) // bring output file along into the JAR
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

distributions {
    main {
        contents {
            from("$buildDir/libs") {
                rename("${rootProject.name}-jvm", rootProject.name)
                into("lib")
            }
        }
    }
}

// Alias "installDist" as "stage" (for cloud providers)
tasks.create("stage") {
    dependsOn(tasks.getByName("installDist"))
}

tasks.getByName<JavaExec>("run") {
    classpath(tasks.getByName<Jar>("jvmJar")) // so that the JS artifacts generated by `jvmJar` can be found and served
}
