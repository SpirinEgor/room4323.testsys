package testsysBackend.api

import com.google.gson.GsonBuilder
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import mu.KLogger
import testsysBackend.database.*

fun Routing.route(database: Database, logger: KLogger) {
    authenticate {
        get("/api/tasks") {
            val tasks = database.getTasks()
            val gson = GsonBuilder()
                    .setPrettyPrinting()
                    .setExclusionStrategies(ExcludeStatement())
                    .create()
            val response = gson.toJson(Tasks(result = Result(tasks)))
            call.respondText(response.toString(), ContentType.Application.Json)
        }
        get("/api/tasks/{prId}") {
            val prId = call.parameters["prId"]?.toInt()
            if (prId == null) {
                call.respond { HttpStatusCode.BadGateway }
            } else {
                val task = database.getTask(prId)
                if (task == null) {
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val response = gson.toJson(TaskById(result = task))
                    call.respondText(response.toString(), ContentType.Application.Json)
                }
            }
        }
        get("/api/tasks/{id}/status") {
            val prId = call.parameters["id"]?.toInt()
            if (prId == null) {
                call.respond { HttpStatusCode.BadGateway }
            } else {
                val task = database.getTask(prId)
                if (task == null) {
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    val submits = database.getSubmits(prId)
                    val gson = GsonBuilder()
                            .setPrettyPrinting()
                            .setExclusionStrategies(ExcludeStatement())
                            .serializeNulls()
                            .create()
                    val response = gson.toJson(TasksIDSubmit(result = SubmitResult(task, submits)))
                    call.respondText(response.toString(), ContentType.Application.Json)
                }
            }
        }
        post("/api/tasks/{id}/submit") {
            val solution = call.receive<Solution>()
            logger.info { solution }
            call.respond(HttpStatusCode.OK)
        }
    }

}