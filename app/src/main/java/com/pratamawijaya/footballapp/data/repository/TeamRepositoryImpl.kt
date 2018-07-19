package com.pratamawijaya.footballapp.data.repository

import android.content.Context
import com.google.gson.Gson
import com.pratamawijaya.footballapp.data.mapper.TeamMapper
import com.pratamawijaya.footballapp.data.model.TeamResponse
import com.pratamawijaya.footballapp.domain.Team
import java.io.BufferedReader
import java.io.InputStreamReader

class TeamRepositoryImpl constructor(private val context: Context,
                                     private val teamMapper: TeamMapper) : TeamRepository {

    /**
     * get teams data
     */
    override fun getTeams(): List<Team> {
        var gson = Gson()
        val inputString = readJsonFromFile("teams.json")

        val response = gson.fromJson(inputString, TeamResponse::class.java)
        var listTeam = mutableListOf<Team>()

        response.teams.map {
            var team = teamMapper.mapFromModel(it)
            listTeam.add(team)
        }

        return listTeam
    }

    /**
     * read json from file
     */
    private fun readJsonFromFile(file: String): String {
        val bufferedReader = BufferedReader(
                InputStreamReader(context.assets.open(file))
        )
        return bufferedReader.use { it.readText() }
    }
}