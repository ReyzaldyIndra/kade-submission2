package app.sub2_footballmatchschedule.model

class LeaguesItem(val idLeague: String?, val strLeague: String?) {
    override fun toString(): String {
        return strLeague.toString()
    }
}