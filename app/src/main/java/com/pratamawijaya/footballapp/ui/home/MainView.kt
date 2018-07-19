package com.pratamawijaya.footballapp.ui.home

import com.pratamawijaya.footballapp.domain.Team

interface MainView {
    fun displayTeams(teams: List<Team>)
}