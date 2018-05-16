package testsysBackend.api

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.google.gson.GsonBuilder
import io.ktor.application.call
import io.ktor.auth.UserHashedTableAuth
import io.ktor.auth.UserPasswordCredential
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders.Authorization
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.post
import io.ktor.util.decodeBase64
import mu.KLogger
import org.apache.commons.codec.digest.DigestUtils.sha256Hex
import testsysBackend.database.*

fun Routing.login(database: Database, logger: KLogger){
    post("/api/login"){
        val post = call.receive<Login>()
        val username = post.username
        val password = post.password
        val user = database.getUserByUsername(username)
        if (user == null || user.pass != sha256Hex(password)) {
            call.respond(HttpStatusCode.Unauthorized)
        } else {
            val userToken = JWTConfig().createToken(user.id)
            val gson = GsonBuilder()
                    .setPrettyPrinting()
                    .setExclusionStrategies(ExcludeStatement())
                    .serializeNulls()
                    .create()
            val response = gson.toJson(UserResult(result = user))
            call.response.headers.append(Authorization, userToken)
            call.respondText(response.toString(), ContentType.Application.Json)
        }
    }
}

class JWTConfig {
    private val secret = "0ynjTRZT9Uk77TnGy_g9Mxi1decLBjKxQK6e2dVzDJo"
    private val issuer = "testsys"
    private val algorithm = Algorithm.HMAC512(secret)

    fun createToken(userId:Int): String = JWT.create()
            .withIssuer(issuer)
            .withClaim("userId", userId)
            .sign(algorithm)
    fun makeJwtVerifier(): JWTVerifier = JWT
            .require(algorithm)
            .withIssuer(issuer)
            .build()
}