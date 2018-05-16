package testsysBackend.judge

import mu.KLogger
import testsysBackend.database.Database
import java.util.*

class TestSystem(val judges: List<IJudge>, private val database: Database, val logger: KLogger) {

    private val queue: Queue<QSubmit>
    private var state: Boolean

    init {
        queue = LinkedList()
        state = false
        test()
    }

    fun putIntoQueue(params: Map<String, String>, submitId: Int) {
        queue.add(QSubmit(params, submitId))
        if (!state) {
            test()
        }
    }

    private fun test() {
        state = true
        while (queue.size > 0) {
            val curSubmit = queue.remove()
            val dbStatus = database.setSubmitStatusRunning(curSubmit.submitId)
            if (!dbStatus) {
                continue
            }
            val result = judges[0].test(curSubmit.params)
            if (true) {
                database.setSubmitVerdictOK(curSubmit.submitId)
            }
        }
        state = false
    }

}

data class QSubmit (
        val params: Map<String, String>,
        val submitId: Int
)