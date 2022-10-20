import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

class SampleTEst : ConfiguracionTest() {

    @Test
    /*Este es un test de prueba en el que simplemente comprobamos que el primer usuario del JSON
    (el que tiene el id 1), se llama Leanne Graham.
    */
    fun test() {
        Given {
            port(3000)
        } When {
            get("/users/{id}",1)
        } Then {
            statusCode(200)
            body("name", equalTo("Leanne Graham"))
        }
    }

    @Test
    /*EJERCICIO Realizar una consulta al endpoint /posts y ver los resultados.
     El resultado que nos tiene que mostrar es un total de 100 ids.
    */
    fun `Consulta al endpoint posts`() {
        Given {
            port(3000)
        } When {
            get("/posts")

        } Then {
            statusCode(200)
            val response = RestTest.doGetRequest("http://localhost:3000/posts")
            val jsonResponse = response.jsonPath().getList<String>("$")
            println(jsonResponse.size)
            val IDs = response.jsonPath().getString("id")
            println(IDs)
        }
    }

    @Test
    /*EJERCICIO Realizar una consulta al endpoint /comments y ver los resultados.
    El resultado que nos tiene que mostrar es un total de 500 ids.
    */
    fun `Consulta al endpoint comments`() {
        Given {
            port(3000)
        } When {
            get("/comments")

        } Then {
            statusCode(200)
            val response = RestTest.doGetRequest("http://localhost:3000/comments")
            val jsonResponse = response.jsonPath().getList<String>("$")
            println(jsonResponse.size)
            val IDs = response.jsonPath().getString("id")
            println(IDs)
        }
    }

}