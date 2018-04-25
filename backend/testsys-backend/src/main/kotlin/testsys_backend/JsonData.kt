package testsys_backend

data class Tasks(
    val status: String,
    val result: Map<String, List<Task>>
)

fun Tasks.getTask(id: Int):Task?{
    val tsk: List<Task>? = result.get("tasks")
    tsk?.forEach{x -> if(x.id == id) return x}
    return null
}
data class Task(
        val id :Int,
        val name : String,
        val result: Map<String, List<Task>>
)

data class Status(val status: String,
                  val result: Result)

fun Status.getLastSubmission():Submission?{
    val submissions = result.submissions
    return submissions?.maxBy { it.id }
}

data class Problem(val id: Int,
                   val name: String)

data class Result(val problem: Problem,
                  val submissions: List<Submission>)

data class Submission(val id: Int,
                      val status: String,
                      val test: String,
                      val comment: String)