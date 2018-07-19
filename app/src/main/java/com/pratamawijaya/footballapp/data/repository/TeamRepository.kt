package com.pratamawijaya.footballapp.data.repository

interface TeamRepository {
    fun getTeams(league:String) : String
}