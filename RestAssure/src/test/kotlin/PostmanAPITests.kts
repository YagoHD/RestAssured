import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.http.HttpStatus


class SampleTEst : ConfiguracionTest() {

    @Test
            /*Este es un test de prueba en el que simplemente comprobamos que el primer usuario del JSON
            (el que tiene el id 1), se llama Leanne Graham.✔️
            */
    fun test() {
        Given {
            port(3000)
        } When {
            get("/users/{id}", 1)
        } Then {
            statusCode(200)
            body("name", equalTo("Leanne Graham"))
        }
    }

    @Test
    fun `given posts when get request triggered then status code 200 and 100 results obtained`() {
        Given {
            port(3000)
        } When {
            get("/posts")

        } Then {
            statusCode(200)
            val response = RestTest.doGetRequest("http://localhost:3000/posts")
            val jsonResponse = response.jsonPath().getList<String>("$")
            println(jsonResponse.size)
            val ids = response.jsonPath().getString("id")
            println(ids)
        }
    }

    @Test
    fun `given comments when get request triggered then status code 200 and 500 results obtained`() {
        Given {
            port(3000)
        } When {
            get("/comments")

        } Then {
            statusCode(200)
            val response = RestTest.doGetRequest("http://localhost:3000/comments")
            val jsonResponse = response.jsonPath().getList<String>("$")
            println(jsonResponse.size)
            val ids = response.jsonPath().getString("id")
            println(ids)
        }
    }

    @Test
    fun `given comments when get request triggered then status code 200 and a pagination of 3 and 5 elements obtained`() {
        Given {
            port(3000)
        } When {
            get("/comments")
        } Then {
            statusCode(200)
            val response = RestTest.doGetRequest("http://localhost:3000/comments?_page=3&_limit=5")
            val jsonResponse = response.jsonPath().getList<String>("$")
            println(jsonResponse.size)
            println(jsonResponse)
        }
    }

    @Test
    fun `given posts when get request triggered then status code 200 and sorting by id and filtering by aliases obtained`() {
        Given {
            port(3000)
        } When {
            get("/posts")
        } Then {
            statusCode(200)
            val response = RestTest.doGetRequest("http://localhost:3000/posts?_sort=id&_order=asc&q=alias")
            val jsonResponse = response.jsonPath().getList<String>("$")
            println(jsonResponse.size)
        }
    }


    @Test
    fun `given posts when get request triggered then status code 201 and a new post is created`() {
        val datos = PostsBodyRequest(10, 101, "Yago", "Hello World")
        val requestBody = Json.encodeToString(datos)
        println(requestBody)
        Given {
            port(3000)
            body(requestBody)
        } When {
            post("/posts")
        } Then {
            println(requestBody)
            statusCode(HttpStatus.SC_CREATED)
            body(
                "userId", equalTo(datos.userId),
                "title", equalTo(datos.title),
                "body", equalTo(datos.body)
            )
        }
    }

    @Test
    fun `given posts when get request triggered then status code 200 and a post is modified`() {
        val datos = PostsBodyRequest(10, 101, "Yago", "Hello World")
        val requestBody = Json.encodeToString(datos)
        println(requestBody)
        Given {
            port(3000)
            body(requestBody)
        } When {
            patch("/posts/99")
        } Then {
            statusCode(200)
            body(
                "userId", equalTo(datos.userId),
                "title", equalTo(datos.title),
                "body", equalTo(datos.body)
            )
        }
    }

    @Test
    fun `check the endpoint posts delete a record created then get status code 200`() {
        Given {
            port(3000)
        } When {
            delete("/posts/102")
        } Then {
            statusCode(200)
        }
    }
}