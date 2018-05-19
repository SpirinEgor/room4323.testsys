package testsysBackend.judge

import mu.KLogger

interface IJudge {

    val args: List<String>
    val logger: KLogger

    fun connect(): Boolean
    fun test(params: SubmitRequest): CheckerResponse?

}