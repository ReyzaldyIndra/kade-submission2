package app.sub2_footballmatchschedule

import app.sub2_footballmatchschedule.model.TeamsItem

interface DetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetails(dataHomeTeam: List<TeamsItem>, dataAwayTeam: List<TeamsItem>)
}