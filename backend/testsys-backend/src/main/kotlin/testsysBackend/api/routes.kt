package testsysBackend.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import testsysBackend.database.Database

fun Routing.route(database: Database) {
    get("/api/tasks") {
        val tasks = database.getTasks()
        val tasksJson = JsonArray()
        for (task in tasks) {
            val curTaskJson = JsonObject()
            curTaskJson.addProperty("id", task.id)
            curTaskJson.addProperty("name", task.name)
            tasksJson.add(curTaskJson)
        }
        val response = JsonObject()
        response.addProperty("status", "OK")
        response.add("result", tasksJson)
        call.respond(response)
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
                val taskJson = JsonObject()
                taskJson.addProperty("id", task.id)
                taskJson.addProperty("name", task.name)
                taskJson.addProperty("statement", task.statement)
                val response = JsonObject()
                response.addProperty("status", "OK")
                response.add("result", taskJson)
                call.respond(response)
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

                val taskJson = JsonObject()
                taskJson.addProperty("id", task.id)
                taskJson.addProperty("name", task.name)

                val submitsJson = JsonArray()
                for (submit in submits) {
                    val curSubmitJson = JsonObject()
                    curSubmitJson.addProperty("id", submit.id)
                    curSubmitJson.addProperty("status", submit.status)
                    curSubmitJson.addProperty("time", submit.time.toString())
                    curSubmitJson.addProperty("verdict", submit.verdict)
                    submitsJson.add(curSubmitJson)
                }

                val response = JsonObject()
                response.addProperty("status", "OK")
                val result = JsonObject()
                result.add("problem", taskJson)
                result.add("submissions", submitsJson)
                response.add("result", result)
                call.respond(response)
            }
        }
    }
}