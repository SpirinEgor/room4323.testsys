package testsysBackend.database

import java.sql.Time
import com.google.gson.FieldAttributes
import com.google.gson.ExclusionStrategy


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

data class Tasks(
        var status: String = "OK",
        val result:Result
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
        val submitions:List<Submit>
)

data class Result(
        val tasks: Set<Problem>

)