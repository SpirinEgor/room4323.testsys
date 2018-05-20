package testsysBackend.database

import mu.KLogger
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class Database(private val path: String,
               private val logger: KLogger) {

    private var dbStatement: Statement? = null

    fun connect(): String {
        Class.forName("org.sqlite.JDBC")
        return try {
            val conn = DriverManager.getConnection("jdbc:sqlite:$path")
            dbStatement = conn.createStatement()
            "OK"
        } catch (ex: SQLException) {
            ex.message!!
        }
    }

    fun getTasks(): List<Problem> {
        val resultQuery: ResultSet
        val tasks = mutableListOf<Problem>()
        try {
            resultQuery = dbStatement!!.executeQuery(
                    "SELECT * FROM Problems"
            )
        } catch (ex: SQLException) {
            logger.error { ex }
            return tasks
        }
        while (resultQuery.next()) {
            tasks.add(Problem(resultQuery.getInt("id"),
                        resultQuery.getString("name"),
                        resultQuery.getString("path"),
                        resultQuery.getInt("time_limit"),
                        resultQuery.getInt("memory_limit")))
        }
        return tasks
    }

    fun getTask(id: Int): Problem? {
        val resultQuery: ResultSet
        try {
            resultQuery = dbStatement!!.executeQuery(
                    "SELECT * FROM Problems WHERE id = $id"
            )
        } catch (ex: SQLException) {
            logger.error { ex }
            return null
        }
        return Problem(resultQuery.getInt("id"),
                        resultQuery.getString("name"),
                        resultQuery.getString("path"),
                        resultQuery.getInt("time_limit"),
                        resultQuery.getInt("memory_limit"))
    }

    fun getSubmits(userId: Int, prId: Int): List<Submit> {
        val resultQuery: ResultSet
        val submits = mutableListOf<Submit>()
        try {
            resultQuery = dbStatement!!.executeQuery(
                    "SELECT * from Submits where user_id = $userId and pr_id = $prId"
            )
        } catch (ex: SQLException) {
            logger.error { ex }
            return submits
        }
        while (resultQuery.next()) {
            submits.add(Submit(
                    resultQuery.getInt("id"),
                    resultQuery.getInt("pr_id"),
                    resultQuery.getInt("user_id"),
                    resultQuery.getString("status"),
                    resultQuery.getTime("submtime"),
                    resultQuery.getString("docker_return"),
                    resultQuery.getString("verdict"),
                    resultQuery.getInt("testid"),
                    resultQuery.getString("comment")
            ))
        }
        submits.sortBy { it.submTime }
        return submits
    }

    fun getUserByUsername(username: String): User? {
        val resultQuery: ResultSet
        try {
            resultQuery = dbStatement!!.executeQuery(
                    "SELECT * FROM Users WHERE username = '$username'"
            )
        } catch (ex: SQLException) {
            logger.error { ex }
            return null
        }
        return if (resultQuery.next()) {
            User(
                    resultQuery.getInt("id"),
                    resultQuery.getString("username"),
                    resultQuery.getString("fullname"),
                    resultQuery.getString("pass"))
        } else {
            null
        }
    }

    fun setSubmitIntoQueue(userId: Int, prId: Int): Int? {
        val resultQuery: ResultSet
        try {
            dbStatement!!.executeUpdate(
                    "INSERT INTO Submits (pr_id, user_id) VALUES($prId, $userId)"
            )
            resultQuery = dbStatement!!.generatedKeys
            resultQuery.next()
        } catch (ex: SQLException) {
            logger.error { ex }
            return null
        }
        return resultQuery.getInt(1)
    }

    fun setSubmitStatusRunning(submitId: Int): Boolean {
        return try {
            dbStatement!!.executeUpdate(
                    "UPDATE Submits SET status = 'running' where id = $submitId"
            )
            true
        } catch (ex: SQLException) {
            logger.error { ex }
            false
        }
    }

    fun setSubmitVerdictOK(submitId: Int, dockerReturn: String = ""): Boolean {
        return try {
            dbStatement!!.executeUpdate(
                    "UPDATE Submits SET status = 'finished', " +
                            "docker_return = '$dockerReturn', verdict = 'OK' where id = $submitId"
            )
            true
        } catch (ex: SQLException) {
            logger.error { ex }
            false
        }
    }

    fun setSubmitVerdictFail(submitId: Int, dockerReturn: String = "", verdict: String,
                             testId: Int, testComment: String): Boolean {
        return try {
            dbStatement!!.executeUpdate(
                    "UPDATE Submits SET status = 'finished', " +
                            "docker_return = '$dockerReturn', verdict = '$verdict', " +
                            "testid = $testId, comment = '$testComment' where id = $submitId"
            )
            true
        } catch (ex: SQLException) {
            logger.error { ex }
            false
        }
    }

}