package testsysBackend


import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.content.*
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.locations.Locations
import io.ktor.response.respondFile
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.routing
import mu.KotlinLogging
import testsysBackend.api.JWTConfig
import testsysBackend.api.login
import testsysBackend.api.route
import testsysBackend.database.Database
import testsysBackend.judge.DockerJudge
import testsysBackend.judge.IJudge
import testsysBackend.judge.TestSystem
import java.io.File
import java.text.DateFormat

fun Application.main() {

    // create logger
    val logger = KotlinLogging.logger {}

    // connect to database
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

    // connect to judges, create TestSystem
    val judges = listOf<IJudge>(
            DockerJudge()
            )
    val testSystem = TestSystem(judges, database, logger)

    // install env
    val jwtRealm = environment.config.property("jwt.realm").getString()
    install(CORS) {
        anyHost()
        allowCredentials = true
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
            validate{ credentials -> JWTPrincipal(credentials.payload) }
        }
    }
    install(Locations)
    install(CallLogging)
    // install routing
    install(Routing) {
        login(database, logger)
        route(database, logger, testSystem)
        routing {
            static {
                staticRootFolder = File("$path/frontend/build")
                file("systemjs.config.js")
                file("main.js")
                file("systemjs-angular-loader.js")
                file("systemjs.config.extras.js")
                default("index.html")
                static("/node_modules") {
                    staticRootFolder = File("$path/frontend")
                    files("node_modules")
                }
                static("/app") {
                    staticRootFolder = File("$path/frontend/build")
                    files("app")
                }
                static("/styles") {
                    staticRootFolder = File("$path/frontend/build")
                    files("styles")
                }
                static("/images") {
                    staticRootFolder = File("$path/frontend/build")
                    files("images")
                }
            }
        }
    }
}
