package com.pratamawijaya.footballapp.data.mapper

import com.pratamawijaya.footballapp.data.model.TeamModel
import com.pratamawijaya.footballapp.domain.Team

class TeamMapper : DomainMapper<Team, TeamModel> {
    override fun mapFromModel(model: TeamModel): Team {
        return Team(
                name = model.strTeam,
                badge = model.strTeamBadge,
                desc = model.strDescriptionEN
        )
    }

    override fun mapToModel(domain: Team): TeamModel {
        return TeamModel(
                strTeam = domain.name,
                strTeamBadge = domain.badge,
                strDescriptionEN = domain.desc
        )
    }

}