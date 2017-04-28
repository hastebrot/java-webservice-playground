import com.winterbe.expekt.expect
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

//—————————————————————————————————————————————————————————————————————————————————————————————————
// SPECS.
//—————————————————————————————————————————————————————————————————————————————————————————————————

class ServerSpec : StringSpec({
    "length should return size of string" {
        "hello".length shouldBe 5
        expect("hello").to.have.length(5)
    }
})
