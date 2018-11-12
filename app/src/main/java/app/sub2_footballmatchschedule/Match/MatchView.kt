package app.sub2_footballmatchschedule.Match

import app.sub2_footballmatchschedule.model.LiveItem
import app.sub2_footballmatchschedule.model.LeaguesItemResponse

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showLiveList(data: List<LiveItem>)
    fun showLeagueList(data:LeaguesItemResponse)
    fun showEmptyData()
}