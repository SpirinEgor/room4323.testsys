package testsysBackend.api

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.call
import io.ktor.auth.UserHashedTableAuth
import io.ktor.auth.UserPasswordCredential
import io.ktor.http.HttpHeaders.Authorization
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import io.ktor.util.decodeBase64

val hashedUserTable = UserHashedTableAuth(table = mapOf(
        "test" to decodeBase64("VltM4nfheqcJSyH887H+4NEOm2tDuKCl83p5axYXlF0=") // sha256 for "test"
))

fun Routing.login(){
    post("/api/login"){
        val post = call.receive<Login>()
        val username = post.username
        val password = post.password
        val user = hashedUserTable.authenticate(UserPasswordCredential(username,password))
        if (user != null){
            val token = JWTConfig().createToken()
            call.response.headers.append(Authorization,token)
            call.respond(HttpStatusCode.OK)
        }
        else call.respond(HttpStatusCode.Unauthorized)
    }
}

class JWTConfig{
    val secret = "0ynjTRZT9Uk77TnGy_g9Mxi1decLBjKxQK6e2dVzDJo"
    val issuer = "testsys"
    val algorithm = Algorithm.HMAC512(secret)

    fun createToken(): String = JWT.create()
            .withIssuer(issuer)
            .sign(algorithm)
    fun makeJwtVerifier(): JWTVerifier = JWT
            .require(algorithm)
            .withIssuer(issuer)
            .build()
}