package testsysBackend.api

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.call
import io.ktor.auth.UserHashedTableAuth
import io.ktor.auth.UserPasswordCredential
import io.ktor.http.HttpHeaders.Authorization
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import io.ktor.util.decodeBase64
import testsysBackend.database.*



fun Routing.login(database:Database){
    post("/api/login"){
        val post = call.receive<Login>()
        val username = post.username
        val password = post.password
        val userToken = database.getUserToken(username,password)
        if(userToken == null) call.respond(HttpStatusCode.Unauthorized)
        else{
            call.response.headers.append(Authorization,userToken)
            call.respond(HttpStatusCode.OK)
        }
    }
}

class JWTConfig{
    private val secret = "0ynjTRZT9Uk77TnGy_g9Mxi1decLBjKxQK6e2dVzDJo"
    private val issuer = "testsys"
    private val algorithm = Algorithm.HMAC512(secret)

    fun createToken(userId:Int): String = JWT.create()
            .withIssuer(issuer)
            .withClaim("userId",userId)
            .sign(algorithm)
    fun makeJwtVerifier(): JWTVerifier = JWT
            .require(algorithm)
            .withIssuer(issuer)
            .build()
}