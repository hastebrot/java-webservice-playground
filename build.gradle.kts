import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.internal.HasConvention
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

//—————————————————————————————————————————————————————————————————————————————————————————————————
// BUILD SCRIPT.
//—————————————————————————————————————————————————————————————————————————————————————————————————

buildscript {
    repositories {
        gradleScriptKotlin()
    }

    dependencies {
        classpath(kotlinModule("gradle-plugin", "1.1.0"))
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.0-M4")
    }
}

//—————————————————————————————————————————————————————————————————————————————————————————————————
// GRADLE PLUGINS.
//—————————————————————————————————————————————————————————————————————————————————————————————————

plugins {
    java
    application
}

apply {
    plugin("java")
    plugin("kotlin")
    plugin("org.junit.platform.gradle.plugin")
}

//—————————————————————————————————————————————————————————————————————————————————————————————————
// MAVEN DEPENDENCIES.
//—————————————————————————————————————————————————————————————————————————————————————————————————

repositories {
    gradleScriptKotlin()
}

dependencies {
    compile(kotlinModule("stdlib", "1.1.0"))
    compile("org.jooby:jooby-netty:1.1.0")
    compile("org.jooby:jooby-lang-kotlin:1.1.0")

    testCompile("io.kotlintest:kotlintest:2.0.1")
    testCompile("com.winterbe:expekt:0.5.0")
    testCompile("org.jetbrains.spek:spek-api:1.1.0")
    testRuntime("org.jetbrains.spek:spek-junit-platform-engine:1.1.0")
}

//—————————————————————————————————————————————————————————————————————————————————————————————————
// SOURCE SETS.
//—————————————————————————————————————————————————————————————————————————————————————————————————

val sourceSets = java.sourceSets
fun sourceSets(block: SourceSetContainer.() -> Unit) = sourceSets.apply(block)

val SourceSetContainer.main: SourceSet get() = this["main"]
val SourceSetContainer.test: SourceSet get() = this["test"]
fun SourceSetContainer.main(block: SourceSet.() -> Unit) = main.apply(block)
fun SourceSetContainer.test(block: SourceSet.() -> Unit) = test.apply(block)

val SourceSet.kotlin: SourceDirectorySet get() =
    (this as HasConvention).convention.getPlugin<KotlinSourceSet>().kotlin

sourceSets {
    main {
        java.setSrcDirs(files("src"))
        kotlin.setSrcDirs(files("src"))
        resources.setSrcDirs(files("src/res"))
    }
    test {
        java.setSrcDirs(files("test"))
        kotlin.setSrcDirs(files("test"))
        resources.setSrcDirs(files("test/res"))
    }
}

//—————————————————————————————————————————————————————————————————————————————————————————————————
// CONFIGURATION.
//—————————————————————————————————————————————————————————————————————————————————————————————————

application {
    mainClassName = "serverKt"
}
