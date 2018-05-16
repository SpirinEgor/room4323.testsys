package testsysBackend.judge

interface IJudge {

    fun connect(args: List<String>): Boolean
    fun test(params: Map<String, String>): Map<String, String>?

}