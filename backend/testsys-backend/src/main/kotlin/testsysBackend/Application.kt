package testsysBackend

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import io.ktor.routing.routing
import mu.KotlinLogging
import testsysBackend.api.route
import testsysBackend.database.Database

fun Application.main() {

    val logger = KotlinLogging.logger {}
    val path = System.getProperty("user.dir").split("/")
            .dropLast(2).foldRight("", { a, b -> "$a/$b" })
    val database = Database("$path/database/testsys.db",
            logger)

    val databaseConnect = database.connect()
    if (databaseConnect != "OK") {
        logger.error { databaseConnect }
        return
    } else {
        logger.info { "Connected to database" }
    }

    install(CORS) {
//        header(HttpHeaders.AccessControlAllowOrigin)
        anyHost()
        headersOf(HttpHeaders.AccessControlAllowOrigin, "*")
    }
    install(CallLogging)
    routing {
        route(database)
    }
}
