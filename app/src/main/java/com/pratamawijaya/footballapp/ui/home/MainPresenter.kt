package com.pratamawijaya.footballapp.ui.home

import com.pratamawijaya.footballapp.data.repository.TeamRepository

class MainPresenter constructor(private val repo: TeamRepository,
                                private val view: MainView) {

    fun getTeams() {
        view.displayTeams(repo.getTeams())
    }
}