package com.pratamawijaya.footballapp.ui.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.pratamawijaya.footballapp.R
import com.pratamawijaya.footballapp.data.mapper.TeamMapper
import com.pratamawijaya.footballapp.data.repository.TeamRepository
import com.pratamawijaya.footballapp.data.repository.TeamRepositoryImpl
import com.pratamawijaya.footballapp.domain.Team
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_team.view.imgTeam
import kotlinx.android.synthetic.main.item_team.view.teamName
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.wrapContent

class MainActivity : AppCompatActivity(), MainView {

    lateinit var presenter: MainPresenter
    lateinit var repo: TeamRepository
    lateinit var rv: RecyclerView
    private var groupAdapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()

        val mapper = TeamMapper()
        repo = TeamRepositoryImpl(this, mapper)

        presenter = MainPresenter(repo, this)
        presenter.getTeams()
    }

    private fun setupUI() {
        linearLayout {
            rv = recyclerView {
                lparams(width = matchParent, height = wrapContent)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = groupAdapter
            }
        }
    }

    override fun displayTeams(teams: List<Team>) {
        teams.map {
            Log.d("tag", "team -> ${it.name}")
            Section().apply {
                add(TeamItem(it))
                groupAdapter.add(this)
            }
        }
    }
}

class TeamItem constructor(private val team: Team) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.teamName.text = team.name
        Picasso.get().load(team.badge).into(viewHolder.itemView.imgTeam)
    }

    override fun getLayout(): Int = R.layout.item_team

}