package testsysBackend.judge


data class SubmitRequest (
        val language: String,
        val code: String,
        val path_to_task: String,
        val time_limit: Int,
        val memory_limit: Int
)

data class SubmitResponse (
        val status: String,
        val result: CheckerResponse
)

data class CheckerResponse (
        val verdict: String,
        val message: String,
        val tests: List<Test>
)

data class Test (
        val number: Int,
        val comment: String
)