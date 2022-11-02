import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.http.HttpStatus
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test


class PostBodyRequest : ConfiguracionTest() {

    @Test
            /*Este es un test de prueba en el que simplemente comprobamos que el primer usuario del JSON
            (el que tiene el id 1), se llama Leanne Graham.✔️
            */
    fun test() {
        Given {
            spec(requestSpecification)
        } When {
            get("/users/{id}", 1)
        } Then {
            statusCode(HttpStatus.SC_OK)
            body("name", equalTo("Leanne Graham"))
        }
    }

    @Test
    fun `given posts when get request triggered then status code 200 and 100 results obtained`() {
        Given {
            spec(requestSpecification)

        } When {
            get("/posts")

        } Then {
            statusCode(HttpStatus.SC_OK)
            val response = RestTest.doGetRequest("http://localhost:3000/posts")
            val jsonResponse = response.jsonPath().getList<String>("$")

            assertThat(jsonResponse.size, equalTo(100))
        }

    }

    @Test
    fun `given comments when get request triggered then status code 200 and 500 results obtained`() {
        Given {
            spec(requestSpecification)
        } When {
            get("/comments")

        } Then {
            statusCode(HttpStatus.SC_OK)
            val response = RestTest.doGetRequest("http://localhost:3000/comments")
            val jsonResponse = response.jsonPath().getList<String>("$")

            assertThat(jsonResponse.size, equalTo(500))
        }
    }

    @Test
    fun `given comments when get request triggered then status code 200 and a pagination of 3 and 5 elements obtained`() {
        Given {
            spec(requestSpecification)
        } When {
            get("/comments")
        } Then {
            statusCode(HttpStatus.SC_OK)
            val response = RestTest.doGetRequest("http://localhost:3000/comments?_page=3&_limit=5")
            val jsonResponse = response.jsonPath().getList<String>("$")
            val postIds = response.jsonPath().getList<String>("postId")
            println(jsonResponse)

            //val pagina = postIds.joinToString("-")
            //assertThat(postIds, equalTo("3-3-3-3-3"))

            assertThat(postIds[0], equalTo(3))
            assertThat(postIds[1], equalTo(3))
            assertThat(postIds[2], equalTo(3))
            assertThat(postIds[3], equalTo(3))
            assertThat(postIds[4], equalTo(3))
            assertThat(postIds.size, equalTo(5))
        }
    }

    @Test
    fun `given posts when get request triggered then status code 200 and sorting by id and filtering by aliases obtained`() {
        Given {
            spec(requestSpecification)
        } When {
            get("/posts")
        } Then {
            val response = RestTest.doGetRequest("http://localhost:3000/posts?_sort=id&_order=asc&q=alias")
            val jsonResponse = response.jsonPath().getList<String>("$")
            val body: String = jsonResponse.toString()
            print(body)

            val titulos = response.jsonPath().getString("title")
            println(titulos)
            val bodys = response.jsonPath().getString("body")

            statusCode(HttpStatus.SC_OK)
            assertThat(titulos, containsString("alias"))
            assertThat(bodys, containsString("alias"))
        }
    }


    @Test
    fun `given posts when get request triggered then status code 201 and a new post is created`() {
        val datos = PostsBodyRequest(10, 101, "Yago", "Hello World")
        val requestBody = Json.encodeToString(datos)
        println(requestBody)
        Given {
            spec(requestSpecification)
            body(requestBody)
        } When {
            post("/posts")
        } Then {
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
        val datos = PostsBodyRequest(10, 100, "YagoModificado", "Hello World Modificado")
        val requestBody = Json.encodeToString(datos)
        println(requestBody)
        Given {
            spec(requestSpecification)
            body(requestBody)
        } When {
            patch("/posts/99")
        } Then {
            statusCode(HttpStatus.SC_OK)
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
            spec(requestSpecification)
        } When {
            delete("/posts/101")
        } Then {
            statusCode(HttpStatus.SC_OK)
        }
    }
}