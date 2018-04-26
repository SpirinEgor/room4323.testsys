package testsys_backend

import java.sql.Connection
import java.sql.DriverManager

class Database(private val path: String) {

    var conn: Connection? = null

    fun connect(): String {
        conn = DriverManager.getConnection(path)
        return "connected"
    }

}