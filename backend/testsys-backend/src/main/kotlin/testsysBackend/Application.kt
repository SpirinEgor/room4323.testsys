package testsysBackend

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.routing.routing
import mu.KotlinLogging
import testsysBackend.api.route
import testsysBackend.database.Database

fun Application.main() {

    val logger = KotlinLogging.logger {}
    val database = Database("/home/voudy/Documents/room4323.testsys/database/testsys.db",
            logger)

    val databaseConnect = database.connect()
    if (databaseConnect != "OK") {
        logger.error { databaseConnect }
        return
    } else {
        logger.info { "Connected to database" }
    }

    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    routing {
        route(database)
    }
}
