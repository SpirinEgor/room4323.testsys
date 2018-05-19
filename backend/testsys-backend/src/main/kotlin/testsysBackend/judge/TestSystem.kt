package testsysBackend.judge

import mu.KLogger
import testsysBackend.database.Database
import java.util.*

class TestSystem(private val judges: List<IJudge>, private val database: Database, val logger: KLogger) {

    private val queue: Queue<QSubmit>
    private var state: Boolean
    private var busy: Array<Boolean>

    init {
        queue = LinkedList()
        state = false
        busy = Array(judges.size, { false })
        test()
    }

    fun putIntoQueue(params: SubmitRequest, submitId: Int) {
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
            if (!busy[0]) {
                busy[0] = true
                val result = judges[0].test(curSubmit.params)
                busy[0] = true
                if (result != null) {
                    if (result.verdict == "OK") {
                        database.setSubmitVerdictOK(curSubmit.submitId)
                    } else {
                        when (result.verdict) {
                            "CE" -> database.setSubmitVerdictFail(curSubmit.submitId,
                                    "", "CE", 1,
                                    "\"${result.message}\"")
                            "WA" -> database.setSubmitVerdictFail(curSubmit.submitId,
                                    "", "WA", result.tests.last().number,
                                    result.tests.last().comment)
                        }
                    }
                }
            } else {
                queue.add(curSubmit)
            }
        }
        state = false
    }

}

data class QSubmit (
        val params: SubmitRequest,
        val submitId: Int
)