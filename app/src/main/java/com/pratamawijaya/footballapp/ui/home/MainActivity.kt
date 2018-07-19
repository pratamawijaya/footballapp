package com.pratamawijaya.footballapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import com.google.gson.Gson
import com.pratamawijaya.footballapp.R
import com.pratamawijaya.footballapp.data.ApiRepository
import com.pratamawijaya.footballapp.data.mapper.TeamMapper
import com.pratamawijaya.footballapp.data.repository.TeamRepository
import com.pratamawijaya.footballapp.data.repository.TeamRepositoryImpl
import com.pratamawijaya.footballapp.domain.Team
import com.pratamawijaya.footballapp.shared.invisible
import com.pratamawijaya.footballapp.shared.visible
import com.pratamawijaya.footballapp.ui.teamdetail.DetailTeamActivity
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.centerHorizontally
import org.jetbrains.anko.ctx
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.imageView
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.padding
import org.jetbrains.anko.progressBar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.spinner
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

class MainActivity : AppCompatActivity(), MainView, TeamListener {

    lateinit var presenter: MainPresenter
    lateinit var repo: TeamRepository

    private var leagueName = "English Premier League"

    //view
    private lateinit var rv: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner

    private var teamList = mutableListOf<Team>()
    private lateinit var groupAdapter: TeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupPresenter()
        groupAdapter = TeamAdapter(teamList, this)

        setupUI()
    }

    private fun setupPresenter() {
        val mapper = TeamMapper()
        val apiRepository = ApiRepository()
        val gson = Gson()
        repo = TeamRepositoryImpl(this, apiRepository)
        presenter = MainPresenter(repo, this, mapper, gson)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    private fun setupUI() {
        verticalLayout {

            spinner = spinner {
            }.lparams {
                leftMargin = dip(16)
                rightMargin = dip(16)
            }

            swipeRefresh = swipeRefreshLayout {
                relativeLayout {
                    lparams(width = matchParent, height = matchParent)
                    rv = recyclerView {
                        lparams(width = matchParent, height = matchParent)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = groupAdapter
                    }

                    progressBar = progressBar {

                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }


        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getTeams(leagueName)
            }
        }

        swipeRefresh.onRefresh {
            presenter.getTeams(leagueName)
        }
    }

    override fun displayTeams(teams: List<Team>) {
        swipeRefresh.isRefreshing = false

        if (teamList.size > 0) teamList.clear()
        teamList.addAll(teams)
        groupAdapter.notifyDataSetChanged()

        teams.map {
            Log.d("tag", "team -> ${it.name}")
        }
    }

    override fun onTeamClick(team: Team) {
        val intent = Intent(this, DetailTeamActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("team", team)
        intent.putExtras(bundle)

        startActivity(intent)
    }
}

interface TeamListener {
    fun onTeamClick(team: Team)
}

class TeamAdapter(private val teams: List<Team>,
                  private val listener: TeamListener) : RecyclerView.Adapter<TeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(TeamUI().createView(AnkoContext.Companion.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(team = teams[position], listener = listener)
    }

}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val teamBadge: ImageView = view.find(R.id.team_badge)
    private val teamName: TextView = view.find(R.id.team_name)

    fun bindItem(team: Team, listener: TeamListener) {
        teamName.text = team.name
        Picasso.get().load(team.badge).into(teamBadge)
    }
}
//class TeamItem constructor(private val team: Team,
//                           private val listener: TeamListener) : Item() {
//    override fun bind(viewHolder: ViewHolder, position: Int) {
//        viewHolder.itemView.teamName.text = team.name
//        Picasso.get().load(team.badge).into(viewHolder.itemView.imgTeam)
//
//        viewHolder.itemView.setOnClickListener { listener.onTeamClick(team) }
//    }
//
//    override fun getLayout(): Int = TeamUI().createView(AnkoContext.Companion.create())
//}

class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                padding = dip(16)

                imageView {
                    id = R.id.team_badge
                }.lparams {
                    rightMargin = dip(16)
                    width = dip(80)
                    height = dip(80)
                }

                textView {
                    id = R.id.team_name
                    textSize = 16f
                }.lparams {
                    gravity = Gravity.CENTER_VERTICAL
                }
            }
        }
    }

}