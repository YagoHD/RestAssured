

import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.http.HttpStatus
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test

class SampleTEst : ConfiguracionTest() {

    @Test
    fun test() {
        Given {
            port(3000)
        } When {
            get("/users/1")
        } Then {
            statusCode(200)
            body("name", equalTo("Leanne Graham"))
        }
    }
}