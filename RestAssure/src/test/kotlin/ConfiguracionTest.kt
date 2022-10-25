import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.config.LogConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.filter.log.LogDetail
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance

import io.restassured.parsing.Parser
import io.restassured.response.Response

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class ConfiguracionTest {

    companion object {
        lateinit var requestSpecification: RequestSpecification
    }

    //PARSEAR JSON
    object RestTest {
        fun doGetRequest(endpoint: String?): Response {
            RestAssured.defaultParser = Parser.JSON
            return RestAssured.given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .`when`()[endpoint].then().contentType(ContentType.JSON).extract().response()
        }
    }

    @BeforeAll
    fun setup() {
        val logConfig = LogConfig.logConfig()
            .enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)
        val config = RestAssuredConfig.config().logConfig(logConfig)

        requestSpecification = RequestSpecBuilder()
            .setBaseUri("http://localhost:3000")
            .setBasePath("")
            .setContentType(ContentType.JSON)
            .setRelaxedHTTPSValidation()
            .setConfig(config)
            .build()
    }


    @AfterAll
    fun tearDown() {
        RestAssured.reset()
    }
}
