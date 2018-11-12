package app.sub2_footballmatchschedule.network

import app.sub2_footballmatchschedule.BuildConfig

object TheSportsDBApi {
    fun getLiveAll(): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/all_leagues.php"
    }

    fun getMatchPrev(id: String): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/eventspastleague.php?id=${id}"
    }

    fun getMatchNext(id: String): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/eventsnextleague.php?id=${id}"
    }
    fun getTeamsDetail(id:String?): String{
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/lookupteam.php?id=133604${id}"
    }
}