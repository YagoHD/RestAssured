
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
            get("/users/{id}",1)
        } Then {
            statusCode(200)
            body("name", equalTo("Leanne Graham"))
        }
    }

    @Test
    /*EJERCICIO Realizar una consulta al endpoint /posts y ver los resultados.
     El resultado que nos tiene que mostrar es un total de 100 ids.✔️
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
            val ids = response.jsonPath().getString("id")
            println(ids)
        }
    }

    @Test
    /*EJERCICIO Realizar una consulta al endpoint /comments y ver los resultados.
    El resultado que nos tiene que mostrar es un total de 500 ids.✔️
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
            val ids = response.jsonPath().getString("id")
            println(ids)
        }
    }

    /*EJERCICIO Realizar una consulta al endpoint comments con una paginacion de 3 y
    5 elementos como límite por página✔️
    */
    @Test
    fun `paginacion de 3 y 5 elementos en endpoint comments`(){
        Given {
            port(3000)
        }When {
            get("/comments")
        }Then {
            statusCode(200)
            val response = RestTest.doGetRequest("http://localhost:3000/comments?_page=3&_limit=5")
            val jsonResponse = response.jsonPath().getList<String>("$")
            println(jsonResponse.size)
            println(jsonResponse)
        }
    }

    @Test
    /*
    EJERCICIO consulta al endpoint /posts donde ordenes por el campo "id" de manera ascendente y
    filtrando por el texto "alias". El resultado tiene que ser 9 registros.✔️
     */
    fun `consulta al endpoint posts ordenando por id y filtrando por alias`(){
        Given {
            port(3000)
        }When {
            get("/posts")
        }Then {
            statusCode(200)
            val response = RestTest.doGetRequest("http://localhost:3000/posts?_sort=id&_order=asc&q=alias")
            val jsonResponse = response.jsonPath().getList<String>("$")
            println(jsonResponse.size)
        }
    }



    @Test
    /*
    EJERCICIO consulta al endpoint /posts para crear un nuevo registro. Comprobar que el resultado sea correcto.
     */
    fun `consulta endpoint posts creando nuevo registro`(){
        val datos = Datos(10,101,"Yago", "Hello World")
        val requestBody = Json.encodeToString(datos)
        println(datos.userId)
        Given {
            port(3000)
            body(requestBody)
        }When {
            post("/posts/100")
        }Then {
            statusCode(HttpStatus.SC_CREATED)
            body(
                "userId", equalTo(requestBody),
                "title", equalTo(datos.title),
                "body", equalTo(datos.body)
            )
        }
    }

    @Test
    /*
    Consulta el registro creado y actualiza alguno de sus campos.
    Comprueba que el/los campo/s actualizado/s previamente se han modificado correctamente.
    */
    fun `consulta endpoint posts modificando un registro `(){
        val datos = Datos(10,101,"Yago", "Hello World")
        val requestBody = Json.encodeToString(datos)
        Given {
            port(3000)
            body(requestBody)
        }When {
            patch("/posts/99")
        }Then {
            statusCode(200)
            body(
                "userId", equalTo(requestBody),
                "title", equalTo(datos.title),
                "body", equalTo(datos.body)
            )
        }
    }

    @Test
    /*
    Elimina el registro creado. Comprueba que el registro se ha eliminado correctamente. ✔️
    */
    fun `consulta el endpoint posts y eliminar un registro creado`(){
        Given {
            port(3000)
        }When {
            delete("/posts/102")
        }Then {
            statusCode(200)
        }
    }
}