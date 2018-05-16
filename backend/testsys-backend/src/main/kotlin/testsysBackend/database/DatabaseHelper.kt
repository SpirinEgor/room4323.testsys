package testsysBackend.database

import java.sql.Time

data class Problem (
        val id: Int,
        val name: String,
        val statement: String = ""
)

data class Submit (
        val id: Int,
        val prId: Int,
        val userId: Int,
        val status: String,
        val submTime: Time,
        val dockerReturn: String?,
        val verdict: String?,
        val testId: Int,
        val comment: String?
)

data class User (
        val id: Int,
        val username: String,
        val fullname: String,
        val pass: String
)
