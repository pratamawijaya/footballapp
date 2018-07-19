package com.pratamawijaya.footballapp.data.repository

import android.content.Context
import com.pratamawijaya.footballapp.data.ApiRepository
import com.pratamawijaya.footballapp.data.TheSportDBApi
import java.io.BufferedReader
import java.io.InputStreamReader

class TeamRepositoryImpl constructor(private val context: Context,
                                     private val apiRepository: ApiRepository) : TeamRepository {

    /**
     * get teams data
     */
    override fun getTeams(league:String): String {
        return apiRepository.doRequest(TheSportDBApi.getTeams(league))

//        doAsync {
//
//        }
//        val response = apiRepository.doRequest(TheSportDBApi.getTeams(league))

//        val gson = Gson()
//        val inputString = readJsonFromFile("teams.json")
//
//        val response = gson.fromJson(inputString, TeamResponse::class.java)
//        val listTeam = mutableListOf<Team>()
//
//        response.teams.map {
//            var team = teamMapper.mapFromModel(it)
//            listTeam.add(team)
//        }
//
//        return listTeam
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