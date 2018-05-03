package testsysBackend.api

import com.google.gson.*
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import testsysBackend.database.*

fun Routing.route(database: Database) {
    get("/api/tasks") {
        val tasks = database.getTasks()

        val gson = GsonBuilder()
                .setPrettyPrinting()
                .setExclusionStrategies(ExcludeStatement())
                .create()
        val response = gson.toJson(Tasks(result= Result(tasks)))
        call.respondText(response.toString(),ContentType.Application.Json)

    }
    get("/api/tasks/{id}") {
        val prId = call.parameters["id"]?.toInt()
        if (prId == null) {
            call.respond { HttpStatusCode.ExceptionFailed }
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
            call.respond { HttpStatusCode.ExceptionFailed }
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
                val response = gson.toJson(TasksIDSubmit(result = SubmitResult(task,submits)))
                call.respondText(response.toString(), ContentType.Application.Json)
            }
        }
    }
}