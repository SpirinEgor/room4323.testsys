package testsysBackend.database

import mu.KLogger
import java.sql.*

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
                                resultQuery.getString("name")))
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
                        resultQuery.getString("name"))
    }

    fun getSubmits(prId: Int): List<Submit> {
        val resultQuery: ResultSet
        val submits = mutableListOf<Submit>()
        try {
            resultQuery = dbStatement!!.executeQuery(
                    "SELECT * FROM Submits WHERE pr_id = $prId"
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

}