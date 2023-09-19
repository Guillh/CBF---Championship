package com.cbf.championship.matches.service;

import com.cbf.championship.Dto.matechesDto.MatchesDto;
import com.cbf.championship.championships.repository.ChampionshipRepository;
import com.cbf.championship.classifications_table.ClassificationsTable;
import com.cbf.championship.classifications_table.repository.ClassificationTableRepository;
import com.cbf.championship.matches.Matches;
import com.cbf.championship.matches.repository.MatchesRepository;
import com.cbf.championship.teams.repository.TeamsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;


@Service
public class MatchesService {

    private final MatchesRepository matchesRepository;
    private final TeamsRepository teamsRepository;
    private final ChampionshipRepository championshipRepository;
    private final ClassificationTableRepository classificationTableRepository;

    public MatchesService(MatchesRepository matchesRepository, TeamsRepository teamsRepository, ChampionshipRepository championshipRepository, ClassificationTableRepository classificationTableRepository) {
        this.matchesRepository = matchesRepository;
        this.teamsRepository = teamsRepository;
        this.championshipRepository = championshipRepository;
        this.classificationTableRepository = classificationTableRepository;
    }
    @Transactional
    public ResponseEntity<Object> createMatch(MatchesDto matchesDto){

        Matches matches = new Matches();

        dateValidation(matchesDto.getHomeTeam(),matchesDto.getVisitingTeam(), matchesDto.getMatchDate());
        generalValidations(matchesDto.getHomeTeam(), matchesDto.getVisitingTeam(), matchesDto.getChampionshipId());
        if(Objects.nonNull(matchesDto.getChampionshipId())) {
            matches.setChampionshipId(this.championshipRepository.findById(matchesDto.getChampionshipId()).get());
        }
        matches.setHomeTeam(this.teamsRepository.findById(matchesDto.getHomeTeam()).get());
        matches.setVisitingTeam(this.teamsRepository.findById(matchesDto.getVisitingTeam()).get());
        matches.setHomeTeamGoals(0);
        matches.setVisitingTeamGoals(0);
        matches.setMatchDate(matchesDto.getMatchDate());
        matches.setMatchStarted(false);
        matches.setMatechFinised(false);

        checkTeamIsInChampionship(matches.getHomeTeam().getId(),matches.getVisitingTeam().getId(), matches.getId());
        return ResponseEntity.ok(this.matchesRepository.save(matches));
    }
    @Transactional
    public ResponseEntity<Object> startMatch(Integer matchId) {
        Matches matches = this.matchesRepository.findById(matchId).get();
        Random gols = new Random();
        if(matchesRepository.checkStatusStart(matchId)) {
            throw new RuntimeException("Essa partida já começou!");
        }
        if(Objects.nonNull(matches.getChampionshipId())) {
            startAndEndValidation(this.matchesRepository.findChampionshipIdByMatchId(matchId));
        }
        int homeTeamsGoals = gols.nextInt(8);
        int visitingTeamsGoals = gols.nextInt(8);
        matches.setHomeTeamGoals(homeTeamsGoals);
        matches.setVisitingTeamGoals(visitingTeamsGoals);
        matches.setMatchStarted(true);
        this.matchesRepository.save(matches);
        return ResponseEntity.ok("Partida inciada com sucesso!");
    }
    @Transactional
    public ResponseEntity<Object> finishMatch(Integer matchId){

        Matches matches = this.matchesRepository.findById(matchId).get();
        matches.setMatechFinised(true);

        if(matchesRepository.checkStatusFinish(matchId)) {
            throw new RuntimeException("Essa partida já foi finalizada!");
        }
        if(Objects.nonNull(matches.getChampionshipId().getId())) {
            Integer homeTeamId = matches.getHomeTeam().getId();
            Integer visitingTeamId = matches.getVisitingTeam().getId();
            Integer championShipId = matches.getChampionshipId().getId();
            matchReult(matchId, homeTeamId, visitingTeamId, championShipId);
        }
        this.matchesRepository.save(matches);
        return ResponseEntity.ok("Partida finalizada com sucesso!");
    }
    @Transactional
    public void dateValidation(Integer homeTeamId, Integer visitingTeamId, Calendar matchDate ) {
        Calendar minimumDate = Calendar.getInstance();
        minimumDate.add(Calendar.DAY_OF_MONTH,3);

        Calendar yesterdayDate = (Calendar) matchDate.clone();
        Calendar tomorrowDate = (Calendar) matchDate.clone();
        yesterdayDate.add(Calendar.DAY_OF_MONTH, -1);
        tomorrowDate.add(Calendar.DAY_OF_MONTH,1);

        if(matchDate.compareTo(minimumDate) <= 0) {
            throw new RuntimeException("Jogos devem ser marcados com 3 dias de antecedencia!");
        }
        if(this.matchesRepository.dateValidation(homeTeamId,matchDate)) {
            throw new RuntimeException("O time de casa já tem jogo marcado nesse dia!");
        }
        if(this.matchesRepository.dateValidation(visitingTeamId,matchDate)) {
            throw new RuntimeException("O time visitante já tem jogo marcado nesse dia!");
        }
        if(this.matchesRepository.dateValidation(homeTeamId,matchDate)) {
            throw new RuntimeException("O time da casa já tem um jogo marcado no dia anterior!");
        }
        if(this.matchesRepository.dateValidation(visitingTeamId,matchDate)) {
            throw new RuntimeException("O time visitante já tem um jogo marcado no dia anterior!");
        }
        if(this.matchesRepository.dateValidation(homeTeamId,matchDate)) {
            throw new RuntimeException("O time da casa já tem um jogo marcado no dia seguinte!");
        }
        if(this.matchesRepository.dateValidation(visitingTeamId,matchDate)) {
            throw new RuntimeException("O time visitante já tem um jogo marcado no dia seguinte!");
        }
    }
    public void generalValidations(Integer homeTeamId, Integer visitingTeamId, Integer championshipId) {
        if(homeTeamId.equals(visitingTeamId)) {
            throw new RuntimeException("Não é possivel marcar um jogo com dois times iguais!");
        }
        if(this.matchesRepository.countByTeamsIdAndChampionshipId(homeTeamId, visitingTeamId, championshipId)) {
            throw new RuntimeException("Esse jogo já ocorreu neste campeonato");
        }
    }
    public void startAndEndValidation(Integer championshipId) {
        if (!this.championshipRepository.checkChampionshipStartStatus(championshipId)) {
            throw new RuntimeException("A partida só pode iniciar caso o campeonato ja tenha começado!");
        }
        if (this.championshipRepository.checkChampionshipFinishStatus(championshipId)) {
            throw new RuntimeException("A partida só pode iniciar caso o campeonato não tenha terminado!");
        }
    }
    public void checkTeamIsInChampionship(Integer homeTeamId, Integer visitingTeamId, Integer matchId) {
        if(!matchesRepository.checkIfHomeTeamIsOnChampionship(homeTeamId ,matchId)) {
            throw new RuntimeException("O time da casa não pertence a esse campeonato");
        }
        if(!matchesRepository.checkIfVisitingTeamIsOnChampionship(visitingTeamId ,matchId)) {
            throw new RuntimeException("O time visitante não pertence a esse campeonato");
        }
    }
    public void matchReult(Integer matchId, Integer homeTeamId, Integer visitingTeamId, Integer championshipId) {
        ClassificationsTable classificationsTableHome = this.classificationTableRepository.findById(matchesRepository.findClassificationTableIdByTeamIdAndChampionshipId(championshipId, homeTeamId)).orElseThrow(() -> {
            return new RuntimeException("Time não cadastrado na tabela de classificação!");
        });
        ClassificationsTable classificationsTableVisiting = this.classificationTableRepository.findById(matchesRepository.findClassificationTableIdByTeamIdAndChampionshipId(championshipId, visitingTeamId)).orElseThrow(() -> {
            return new RuntimeException("Time não cadastrado na tabela de classificação!");
        });
        Matches matches = this.matchesRepository.findById(matchId).get();

        if(this.matchesRepository.getGoalsOfHomeTeam(matchId) > this.matchesRepository.getGoalsOfVisitingTeam(matchId)){
            classificationsTableHome.setPoints(classificationsTableHome.getPoints() + 3);
            classificationsTableHome.setWin(classificationsTableHome.getWin() + 1);
            classificationsTableVisiting.setLoss(classificationsTableVisiting.getLoss() + 1);
        }
        if(this.matchesRepository.getGoalsOfHomeTeam(matchId) < this.matchesRepository.getGoalsOfVisitingTeam(matchId)) {
            classificationsTableVisiting.setPoints(classificationsTableVisiting.getPoints() + 3);
            classificationsTableVisiting.setWin(classificationsTableVisiting.getWin() + 1);
            classificationsTableHome.setLoss(classificationsTableHome.getLoss() +1);
        }
        if (this.matchesRepository.getGoalsOfHomeTeam(matchId) == this.matchesRepository.getGoalsOfVisitingTeam(matchId)){
            classificationsTableHome.setDraw(classificationsTableHome.getDraw() + 1);
            classificationsTableHome.setPoints(classificationsTableHome.getPoints() + 1);
            classificationsTableVisiting.setDraw(classificationsTableVisiting.getDraw() + 1);
            classificationsTableVisiting.setPoints(classificationsTableVisiting.getPoints() +1);
        }
        classificationsTableHome.setGoalsScored(classificationsTableHome.getGoalsScored() + matches.getHomeTeamGoals());
        classificationsTableHome.setGoalsConceded(classificationsTableHome.getGoalsConceded() + matches.getVisitingTeamGoals());
        classificationsTableHome.setGoalsDifference(classificationsTableHome.getGoalsDifference() + classificationsTableHome.getGoalsScored() - classificationsTableHome.getGoalsConceded());
        classificationsTableVisiting.setGoalsScored(classificationsTableVisiting.getGoalsScored() + matches.getVisitingTeamGoals());
        classificationsTableVisiting.setGoalsConceded(classificationsTableVisiting.getGoalsConceded() + matches.getHomeTeamGoals());
        classificationsTableVisiting.setGoalsDifference(classificationsTableVisiting.getGoalsDifference() + matches.getVisitingTeamGoals() - matches.getHomeTeamGoals());

        this.classificationTableRepository.save(classificationsTableHome);
        this.classificationTableRepository.save(classificationsTableVisiting);
    }
}
