package app.sub2_footballmatchschedule.Match


import app.sub2_footballmatchschedule.model.*
import app.sub2_footballmatchschedule.network.ApiRepository
import app.sub2_footballmatchschedule.network.TheSportsDBApi
import app.sub2_footballmatchschedule.utils.ContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg



class MatchPresenter(private val view: MatchView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: ContextProvider = ContextProvider()) {


    var menu = 1

    fun getLiveAll() {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportsDBApi.getLiveAll()),
                        LeaguesItemResponse::class.java
                )
            }
                view.hideLoading()
                view.showLeagueList(data.await())

        }
    }
    fun getMatchPrev(id: String) {
        menu = 1
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportsDBApi.getMatchPrev(id)),
                        LiveItemResponse::class.java
                )
            }

            view.hideLoading()

            try {
                view.showLiveList(data.await().live!!)
            } catch (e: NullPointerException) {
                view.showEmptyData()
            }
        }
    }

    fun getMatchNext(id: String) {
        menu = 2
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportsDBApi.getMatchNext(id)),
                        LiveItemResponse::class.java
                )
            }

            view.hideLoading()

            try {
                view.showLiveList(data.await().live!!)
            } catch (e: NullPointerException) {
                view.showEmptyData()
            }
        }
    }

}