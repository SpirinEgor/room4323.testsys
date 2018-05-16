package testsysBackend.judge

class DockerJudge: IJudge {

    override fun connect(args: List<String>): Boolean {
        return true
    }

    override fun test(params: Map<String, String>): Map<String, String>? {
        return null
    }
}