package com.pratamawijaya.footballapp.data.repository

import com.pratamawijaya.footballapp.domain.Team

interface TeamRepository {
    fun getTeams() : List<Team>
}