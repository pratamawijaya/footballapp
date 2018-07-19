package com.pratamawijaya.footballapp.ui.home

import com.google.gson.Gson
import com.pratamawijaya.footballapp.data.mapper.TeamMapper
import com.pratamawijaya.footballapp.data.model.TeamResponse
import com.pratamawijaya.footballapp.data.repository.TeamRepository
import com.pratamawijaya.footballapp.domain.Team
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter constructor(private val repo: TeamRepository,
                                private val view: MainView,
                                private val teamMapper: TeamMapper,
                                private val gson:Gson) {

    fun getTeams(league:String) {
        view.showLoading()

        doAsync {
            val data = repo.getTeams(league)
            val response = gson.fromJson(data, TeamResponse::class.java)
            val listTeam = mutableListOf<Team>()

            response.teams.map {
                var team = teamMapper.mapFromModel(it)
                listTeam.add(team)
            }

            uiThread {
                view.hideLoading()
                view.displayTeams(listTeam)
            }
        }
    }
}