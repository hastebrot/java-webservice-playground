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
}

//—————————————————————————————————————————————————————————————————————————————————————————————————
// MAVEN DEPENDENCIES.
//—————————————————————————————————————————————————————————————————————————————————————————————————

repositories {
    gradleScriptKotlin()
}

dependencies {
    compile(kotlinModule("stdlib", "1.1.0"))
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
        resources.setSrcDirs(files())
    }
    test {
        java.setSrcDirs(files("test"))
        kotlin.setSrcDirs(files("test"))
        resources.setSrcDirs(files())
    }
}

//—————————————————————————————————————————————————————————————————————————————————————————————————
// CONFIGURATION.
//—————————————————————————————————————————————————————————————————————————————————————————————————

application {
    mainClassName = "mainKt"
}
