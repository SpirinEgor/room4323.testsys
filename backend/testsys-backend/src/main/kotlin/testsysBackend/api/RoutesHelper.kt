package testsysBackend.api

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import testsysBackend.database.Problem
import testsysBackend.database.Submit

class ExcludeStatement : ExclusionStrategy {

    override fun shouldSkipClass(arg0: Class<*>): Boolean {
        return false
    }

    override fun shouldSkipField(f: FieldAttributes): Boolean {
        return (f.declaringClass == Problem::class.java && f.name == "statement") ||
                (f.declaringClass == Submit::class.java && (f.name == "prId" ||
                f.name == "userId" ||
                f.name == "dockerReturn"))
    }
}

data class Tasks(
        var status: String = "OK",
        val result: Result
)

data class TaskById(
        var status: String = "OK",
        val result: Problem
)

data class TasksIDSubmit(
        var status: String = "OK",
        val result: SubmitResult
)

data class SubmitResult(
        val problem: Problem,
        val submissions: List<Submit>
)

data class Result(
        val tasks: List<Problem>
)

data class Solution(
        val code: String
)

data class Login(
        val username: String,
        val password: String
)
