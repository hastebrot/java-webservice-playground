import org.jooby.run

//—————————————————————————————————————————————————————————————————————————————————————————————————
// MAIN FUNCTION.
//—————————————————————————————————————————————————————————————————————————————————————————————————

fun main(args: Array<String>) {
    run(*args) {
        get {
            val name = param("name").value("Kotlin")
            "Hello $name"
        }
    }
}
