package com.pratamawijaya.footballapp.ui.home

import com.pratamawijaya.footballapp.domain.Team

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun displayTeams(teams: List<Team>)
}