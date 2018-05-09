package testsysBackend.api

import io.ktor.application.*
import io.ktor.auth.UserHashedTableAuth
import io.ktor.auth.UserPasswordCredential
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.decodeBase64
import testsysBackend.Login
import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import io.ktor.http.HttpHeaders.Authorization

val hashedUserTable = UserHashedTableAuth(table = mapOf(
        "test" to decodeBase64("VltM4nfheqcJSyH887H+4NEOm2tDuKCl83p5axYXlF0=") // sha256 for "test"
))

fun Routing.login(){
    post<Login>{
        val post = call.receive<Parameters>()
        val username = post["username"] ?: ""
        val password = post["password"] ?: ""
        val user = hashedUserTable.authenticate(UserPasswordCredential(username,password))
        if(user!=null){
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