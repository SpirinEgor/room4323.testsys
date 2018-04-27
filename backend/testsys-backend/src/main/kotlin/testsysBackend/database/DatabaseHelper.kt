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
        val time: Time,
        val verdict: String
)