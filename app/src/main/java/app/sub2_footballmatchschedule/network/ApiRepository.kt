package app.sub2_footballmatchschedule.network

import java.net.URL

class ApiRepository {
    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}