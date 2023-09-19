package com.cbf.championship.teams.service;

import com.cbf.championship.teams.Teams;
import com.cbf.championship.teams.repository.TeamsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TeamsService {
    private final TeamsRepository teamsRepository;

    public TeamsService(TeamsRepository teamsRepository) {
        this.teamsRepository = teamsRepository;
    }
    @Transactional
    public Object createTeam(Teams teams) {
        if(this.teamsRepository.countTeamsByName(teams.getName())) {
            throw new RuntimeException("Já existe um time com este nome!");
        }
        return this.teamsRepository.save(teams);
    }

    @Transactional
    public ResponseEntity<Object> replaceTeam(Integer id, Teams teams){
        if(this.teamsRepository.countTeamsByName(teams.getName())) {
            throw new RuntimeException("Já existe um time com este nome!");
        }
        Teams team = this.teamsRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Time não encotrado!");
        });

        team.setName(teams.getName());
        return ResponseEntity.ok(this.teamsRepository.save(team));
    }

    public ResponseEntity<Object> deleteTeam(Integer id) {
        if(this.teamsRepository.countTeamsInChampionship(id)) {
            throw new RuntimeException("Esse time já participou ou esta participando de um campeonato!");
        }
        if(teamsRepository.countTeamsInMateches(id)) {
            throw new RuntimeException("Esse time já participou de jogos!");
        }

        this.teamsRepository.deleteById(id);
        return ResponseEntity.ok("Time deletador com sucesso");
    }
}
