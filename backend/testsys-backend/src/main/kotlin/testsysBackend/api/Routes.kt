package testsysBackend.api

import com.google.gson.GsonBuilder
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import mu.KLogger
import testsysBackend.database.Database
import testsysBackend.judge.SubmitRequest
import testsysBackend.judge.TestSystem

fun Routing.route(database: Database, logger: KLogger, testSystem: TestSystem) {
    authenticate {
        get("/api/tasks") {
            val tasks = database.getTasks()
            val gson = GsonBuilder()
                    .setPrettyPrinting()
                    .setExclusionStrategies(ExcludeStatement())
                    .serializeNulls()
                    .create()
            val response = gson.toJson(TasksResult(result = ProblemList(tasks)))
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
                    val gson = GsonBuilder()
                            .setPrettyPrinting()
                            .setExclusionStrategies(ExcludeStatement())
                            .serializeNulls()
                            .create()
                    val response = gson.toJson(TaskByIdResult(result = task))
                    call.respondText(response.toString(), ContentType.Application.Json)
                }
            }
        }
        get("/api/tasks/{id}/status") {
            val prId = call.parameters["id"]?.toInt()
            val userId = call.authentication.principal<JWTPrincipal>()
                        ?.payload!!.claims["userId"]?.asInt()
            if (prId == null || userId == null) {
                call.respond { HttpStatusCode.BadGateway }
            } else {
                val task = database.getTask(prId)
                if (task == null) {
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    val submits = database.getSubmits(userId, prId)
                    val gson = GsonBuilder()
                            .setPrettyPrinting()
                            .setExclusionStrategies(ExcludeStatement())
                            .serializeNulls()
                            .create()
                    val response = gson.toJson(TaskSubmitsResult(result = Submits(task, submits)))
                    call.respondText(response.toString(), ContentType.Application.Json)
                }
            }
        }
        post("/api/tasks/{id}/submit") {
            val solution = call.receive<Solution>()
            val userId = call.authentication.principal<JWTPrincipal>()
                        ?.payload!!.claims["userId"]?.asInt()
            val prId = call.parameters["id"]?.toInt()
            if (prId == null || userId == null) {
                call.respond(HttpStatusCode.NotFound)
            }
            val submitId = database.setSubmitIntoQueue(userId!!, prId!!)
            if (submitId == null) {
                call.respond(HttpStatusCode.BadRequest)
            }
            val params = SubmitRequest(
                    solution.language,
                    solution.code,
                    "sum",
                    100,
                    100
            )
            testSystem.putIntoQueue(params, submitId!!)
            val gson = GsonBuilder()
                    .setPrettyPrinting()
                    .setExclusionStrategies(ExcludeStatement())
                    .serializeNulls()
                    .create()
            val response = gson.toJson(OKResult())
            call.respondText(response.toString(), ContentType.Application.Json)
        }
        get("/api/languages") {
            val languages = Language(listOf("C++", "Python", "C", "Java", "Kotlin"))
            val gson = GsonBuilder()
                    .setPrettyPrinting()
                    .setExclusionStrategies(ExcludeStatement())
                    .serializeNulls()
                    .create()
            val response = gson.toJson(LanguagesResult(result = languages))
            call.respondText(response.toString(), ContentType.Application.Json)
        }
    }

}