package app.sub2_footballmatchschedule

import app.sub2_footballmatchschedule.model.TeamsItemResponse
import app.sub2_footballmatchschedule.network.ApiRepository
import app.sub2_footballmatchschedule.network.TheSportsDBApi
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import app.sub2_footballmatchschedule.utils.ContextProvider

class DetailPresenter(private val view: DetailView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: ContextProvider = ContextProvider()) {
    fun getTeamDetails(idHomeTeam: String?, idAwayTeam: String?) {
        view.showLoading()

        async(context.main) {
            val dataHomeTeam = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportsDBApi.getTeamsDetail(idHomeTeam.toString())),
                        TeamsItemResponse::class.java
                )
            }

            val dataAwayTeam = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportsDBApi.getTeamsDetail(idAwayTeam.toString())),
                        TeamsItemResponse::class.java
                )
            }

            view.hideLoading()
            view.showTeamDetails(dataHomeTeam.await().teams!!, dataAwayTeam.await().teams!!)
        }
    }
}