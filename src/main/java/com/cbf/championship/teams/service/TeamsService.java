package com.cbf.championship.teams.service;

import com.cbf.championship.teams.Teams;
import com.cbf.championship.teams.repository.TeamsRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamsService {
    private final TeamsRepository teamsRepository;

    public TeamsService(TeamsRepository teamsRepository) {
        this.teamsRepository = teamsRepository;
    }

    public Object createTeam(Teams teams) {
        if(this.teamsRepository.countTeamsByName(teams.getName())) {
            throw new RuntimeException("Já existe um time com este nome!");
        }
        return this.teamsRepository.save(teams);
    }
}
