package app.sub2_footballmatchschedule.Match

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.*
import app.sub2_footballmatchschedule.adapter.MatchAdapter
import app.sub2_footballmatchschedule.model.LeaguesItem
import app.sub2_footballmatchschedule.model.LeaguesItemResponse
import app.sub2_footballmatchschedule.model.LiveItem
import app.sub2_footballmatchschedule.model.TeamsItem
import app.sub2_footballmatchschedule.utils.invisible
import app.sub2_footballmatchschedule.utils.visible
import app.sub2_footballmatchschedule.R
import app.sub2_footballmatchschedule.network.ApiRepository
import app.sub2_footballmatchschedule.utils.progressBar
import com.google.gson.Gson
import com.omrobbie.footballmatchschedule.mvp.detail.DetailActivity
import com.omrobbie.footballmatchschedule.mvp.detail.INTENT_DETAIL
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView
import org.jetbrains.anko.recyclerview.v7.recyclerView


class MatchActivity : AppCompatActivity(), MatchView {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar : ProgressBar
    private lateinit var spinnerLayout : LinearLayout
    private lateinit var spinner: Spinner
    private lateinit var adapter: MatchAdapter
    private lateinit var presenter: MatchPresenter
    private lateinit var emptyDataView: LinearLayout
    private lateinit var league : LeaguesItem
    private var live: MutableList<LiveItem> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startLayout()
        startEnv()
    }
    override fun showLoading() {
        progressBar.visible()
        recyclerView.invisible()
        emptyDataView.invisible()
        spinnerLayout.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        recyclerView.visible()
        emptyDataView.invisible()
    }

    override fun showEmptyData() {
        progressBar.invisible()
        recyclerView.invisible()
        emptyDataView.visible()
    }

    override fun showLeagueList(data: LeaguesItemResponse) {
        spinner.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, data.leagues)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                league = spinner.selectedItem as LeaguesItem

                when (presenter.menu) {
                    1 -> presenter.getMatchPrev(league.idLeague!!)
                    2 -> presenter.getMatchNext(league.idLeague!!)
                }
            }
        }
    }


    override fun showLiveList(data: List<LiveItem>) {
        showLiveData(data)
    }

    private fun startLayout() {
        linearLayout {
            orientation = LinearLayout.VERTICAL

            spinnerLayout = linearLayout {
                orientation = LinearLayout.VERTICAL
                backgroundColor = Color.LTGRAY

                spinner = spinner {
                    id = R.id.spinner
                    padding = dip(16)
                    minimumHeight = dip(80)
                }
            }
            relativeLayout {
                emptyDataView = linearLayout {
                    orientation = LinearLayout.VERTICAL

                    textView {
                        gravity = Gravity.CENTER
                        padding = dip(8)
                        text = "No Data Provided"
                    }
                }.lparams {
                    centerInParent()
                }

                recyclerView = recyclerView {
                    id = R.id.recycler_view
                    layoutManager = LinearLayoutManager(ctx)
                }.lparams(matchParent, matchParent) {
                    topOf(R.id.bottom_navigation_view)
                }

                progressBar(R.id.progress_bar).lparams {
                    centerInParent()
                }

                bottomNavigationView {
                    backgroundColor = Color.WHITE
                    menu.apply {
                        add(0, R.id.last_match, 0, "Last Match")
//                                .setIcon(R.drawable.ic_launcher_background)
                                .setOnMenuItemClickListener {
                                    presenter.getMatchPrev(league.idLeague!!)
                                    false
                                }
                        add(0,R.id.next_match,0, "Next Match")
//                                .setIcon(R.drawable.ic_)
                                .setOnMenuItemClickListener {
                                    presenter.getMatchNext(league.idLeague!!)
                                    false
                                }
                    }
                }.lparams(matchParent, wrapContent){
                    alignParentBottom()

                }
            }
        }
    }
    private fun startEnv(){
        progressBar = find(R.id.progress_bar)
        presenter = MatchPresenter(this, ApiRepository(), Gson())
        adapter = MatchAdapter(live) {
            startActivity<DetailActivity>(INTENT_DETAIL to it)
        }

        presenter.getLiveAll()
        recyclerView.adapter = adapter
    }

    private fun showLiveData(data: List<LiveItem>) {
        live.clear()
        live.addAll(data)
        adapter.notifyDataSetChanged()
        recyclerView.scrollToPosition(0)
    }

}
