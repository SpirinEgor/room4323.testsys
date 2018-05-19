package testsysBackend.judge

import com.google.gson.GsonBuilder
import khttp.get
import khttp.post
import mu.KLogger
import org.json.JSONObject
import testsysBackend.api.ExcludeStatement

class DockerJudge(override val args: List<String>, override val logger: KLogger): IJudge {

    override fun connect(): Boolean {
        val r = get(args[0])
        return r.statusCode == 200
    }

    override fun test(params: SubmitRequest): CheckerResponse? {
        val jsonParams = JSONObject(params)
        val r = post(args[0], json = jsonParams)
        val rJson = r.jsonObject
        if (r.statusCode != 200 || rJson["status"] != "OK") {
            return null
        }
        val resultJson = rJson.getJSONObject("result")
        val tests = mutableListOf<Test>()
        for (testJsonIndex in 0..(resultJson.getJSONArray("tests").length() - 1)) {
            val testJson = resultJson.getJSONArray("tests").getJSONObject(testJsonIndex)
            tests.add(Test(
                    testJson.getInt("test_number"),
                    testJson.getString("checker_comment")
            ))
        }
        return CheckerResponse(
                resultJson.getString("verdict"),
                resultJson.getString("message"),
                tests
        )
    }
}