package testsysBackend


import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import io.ktor.locations.Locations
import io.ktor.routing.Routing
import mu.KotlinLogging
import testsysBackend.api.JWTConfig
import testsysBackend.api.login
import testsysBackend.api.route
import testsysBackend.database.Database
import java.text.DateFormat

fun Application.main() {

    val logger = KotlinLogging.logger {}

    val path = System.getProperty("user.dir").split("/")
            .dropLast(2).foldRight("", { a, b -> "$a/$b" })
    val database = Database("$path/database/testsys.db",
            logger)

    val jwtRealm = environment.config.property("jwt.realm").getString()

    val databaseConnect = database.connect()
    if (databaseConnect != "OK") {
        logger.error { databaseConnect }
        return
    } else {
        logger.info { "Connected to database" }
    }

    install(CORS) {
        anyHost()
        headersOf(HttpHeaders.AccessControlAllowOrigin, "*")
    }
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }
    install(Authentication) {
        jwt {
            val jwtVerifier = JWTConfig().makeJwtVerifier()
            this@jwt.realm = jwtRealm
            verifier(jwtVerifier)
            validate{credentials -> JWTPrincipal(credentials.payload)}
        }
    }
    install(Locations)
    install(CallLogging)
    install(Routing) {
        login()
        route(database, logger)
    }
}
