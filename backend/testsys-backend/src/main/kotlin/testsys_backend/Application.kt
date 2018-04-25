package testsys_backend

import com.google.gson.GsonBuilder
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing

fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {
        route("/api") {
            get("tasks") {
                val fileContent = this.javaClass.getResource("/tasks.json").readText()
                val gson = GsonBuilder().create()
                val tasks: Tasks = gson.fromJson(fileContent, Tasks::class.java)

                call.respond(tasks)
            }
            get("tasks/{id}") {
                val fileContent = this.javaClass.getResource("/tasks.json").readText()
                val gson = GsonBuilder().create()
                val tasks: Tasks = gson.fromJson(fileContent, Tasks::class.java)
                //try catch
                val id = call.parameters["id"]?.toInt()
                if (id == null) {
                    call.respond { HttpStatusCode.ExceptionFailed }
                } else {
                    val item = tasks.getTask(id)
                    if (item == null) {
                        call.respond(HttpStatusCode.NotFound)
                    } else {
                        call.respond(item)
                    }
                }
            }
            get("getStatus/{id}") {
                val fileContent = this.javaClass.getResource("/status.json").readText()
                val gson = GsonBuilder().create()
                val status: Status = gson.fromJson(fileContent, Status::class.java)
                // try catch
                call.respond(status)
                val id = call.parameters["id"]?.toInt()
                if (id == null) {
                    call.respond { HttpStatusCode.ExceptionFailed }
                } else {
                    val item = status.getLastSubmission()
                    if (item == null) {
                        call.respond(HttpStatusCode.NotFound)
                    } else {
                        call.respond(item)
                    }
                }
            }
        }
    }
}
