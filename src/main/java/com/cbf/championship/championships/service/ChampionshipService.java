package com.cbf.championship.championships.service;


import com.cbf.championship.Dto.teamsDto.TeamsDto;
import com.cbf.championship.championships.Championships;
import com.cbf.championship.championships.repository.ChampionshipRepository;
import com.cbf.championship.classifications_table.ClassificationsTable;
import com.cbf.championship.classifications_table.repository.ClassificationTableRepository;
import com.cbf.championship.teams.repository.TeamsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

@Service
public class ChampionshipService {
    private final ChampionshipRepository championshipRepository;
    private final TeamsRepository teamsRepository;
    private final ClassificationTableRepository classificationTableRepository;
    private TeamsDto teamsDto;

    public ChampionshipService(ChampionshipRepository championshipRepository, TeamsRepository teamsRepository, ClassificationTableRepository classificationTableRepository) {
        this.championshipRepository = championshipRepository;
        this.teamsRepository = teamsRepository;
        this.classificationTableRepository = classificationTableRepository;
    }

    public ResponseEntity<Object> createChampionship(Championships championships) {
        Calendar date = Calendar.getInstance();
        if (championships.getChampionshipDate() < date.get(Calendar.YEAR)) {
            throw new RuntimeException("Não é possivel criar campeonatos em anos passados!");
        }
        if (this.championshipRepository.countChampionshipsByNameAndDate(championships.getChampionshipName(), championships.getChampionshipDate())) {
            throw new RuntimeException("Um campeonato com esse mesmo nome ja aconteceu ou esta acontecendo neste ano!");
        }

        championships.setChampionshipName(championships.getChampionshipName());
        championships.setChampionshipDate(championships.getChampionshipDate());
        championships.setChampionshipStarted(false);
        championships.setChampionshipFinished(false);
        return ResponseEntity.ok(this.championshipRepository.save(championships));
    }

    @Transactional
    public ResponseEntity<Object> startChampioship(TeamsDto teamsDto) {

        List<Integer> teams = teamsDto.getTeams();
        for (Integer team: teams) {

            ClassificationsTable classificationsTable = new ClassificationsTable();

            classificationsTable.setChampionshipId(this.championshipRepository.findById(teamsDto.getChampionshipId()).get());
            classificationsTable.setTeam(this.teamsRepository.findById(team).get());
            classificationsTable.setPoints(0);
            classificationsTable.setWin(0);
            classificationsTable.setDraw(0);
            classificationsTable.setLoss(0);
            classificationsTable.setGoalsScored(0);
            classificationsTable.setGoalsConceded(0);
            classificationsTable.setGoalsDifference(0);

            this.classificationTableRepository.save(classificationsTable);
        }
        Championships championships = this.championshipRepository.findById(teamsDto.getChampionshipId()).get();
        championships.setChampionshipStarted(true);
        this.championshipRepository.save(championships);
        return ResponseEntity.ok("Campeonato inciado com sucesso!");
    }

    public ResponseEntity<Object> finishChampionship(TeamsDto teamsDto) {
        Integer teamsTotal = this.championshipRepository.countTeamsByChampionshipId(teamsDto.getChampionshipId());
        Integer gamesPlayed = teamsTotal * (teamsTotal - 1);
        if(!this.championshipRepository.countGamesPlayedByChampionshipId(teamsDto.getChampionshipId()).equals(gamesPlayed)) {
            throw new RuntimeException("Todos os jogos devem ser realizado antes de finalizar um campeonato!");
        }
        Championships championships = this.championshipRepository.findById(teamsDto.getChampionshipId()).get();
        championships.setChampionshipFinished(true);
        this.championshipRepository.save(championships);
        return ResponseEntity.ok("Campeonato finalizado com suceso!");
    }
}
