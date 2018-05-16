package testsysBackend.api

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import testsysBackend.database.Problem
import testsysBackend.database.Submit
import testsysBackend.database.User


class ExcludeStatement : ExclusionStrategy {

    override fun shouldSkipClass(arg0: Class<*>) = false

    override fun shouldSkipField(f: FieldAttributes) =
        (f.declaringClass == Submit::class.java && (f.name == "prId" ||
                                                    f.name == "userId" ||
                                                    f.name == "dockerReturn")) ||
        (f.declaringClass == User::class.java && f.name == "pass")
}

data class OKResult (
        var status: String = "OK"
)

data class TasksResult (
        var status: String = "OK",
        val result: ProblemList
)

data class TaskByIdResult (
        val status: String = "OK",
        val result: Problem
)

data class TaskSubmitsResult (
        val status: String = "OK",
        val result: Submits
)

data class UserResult (
        val status: String = "OK",
        val result: User
)


data class Submits (
        val problem: Problem,
        val submissions: List<Submit>
)

data class ProblemList (
        val tasks: List<Problem>
)

data class Solution (
        val language: String,
        val code: String
)

data class Login (
        val username: String,
        val password: String
)
