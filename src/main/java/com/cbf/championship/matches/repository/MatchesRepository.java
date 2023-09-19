package com.cbf.championship.matches.repository;

import com.cbf.championship.matches.Matches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Calendar;

@Repository
public interface MatchesRepository extends JpaRepository<Matches, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) > 0 " +
                    "FROM matches m " +
                    "WHERE (m.home_team = :teamId " +
                    "OR m.visiting_team = :teamId) " +
                    "AND m.match_date = :matchDate ")
    boolean dateValidation(@Param("teamId") Integer teamId, @Param("matchDate") Calendar matchDate);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) > 0 " +
                    "FROM matches m " +
                    "WHERE m.championship_id = :championshipId " +
                    "AND m.home_team = :homeTeam " +
                    "AND m.visiting_team = :visitingTeam ")
    boolean countByTeamsIdAndChampionshipId(@Param("championshipId") Integer championshipId, @Param("homeTeam") Integer homeTeam, @Param("visitingTeam") Integer visitingTeam);

    @Query(nativeQuery = true,
            value = "SELECT m.championship_id " +
                    "FROM matches m " +
                    "WHERE m.match_id = :matchId ")
    Integer findChampionshipIdByMatchId(@Param("matchId") Integer matchId);

    @Query(nativeQuery = true,
            value = "SELECT m.home_team_goals " +
                    "FROM matches m " +
                    "WHERE m.match_id = :matchId ")
    Integer getGoalsOfHomeTeam(@Param("matchId") Integer matchId);

    @Query(nativeQuery = true,
            value = "SELECT m.visiting_team_goals " +
                    "FROM matches m " +
                    "WHERE m.match_id = :matchId ")
    Integer getGoalsOfVisitingTeam(@Param("matchId") Integer matchId);

    @Query(nativeQuery = true,
            value = "SELECT ct.classification_table_id " +
                    "FROM classifications_table ct " +
                    "WHERE ct.championship_id = :championshipId " +
                    "AND ct.team = :teamId ")
    Integer findClassificationTableIdByTeamIdAndChampionshipId(@Param("championshipId") Integer championshipId, @Param("teamId") Integer teamId);

    @Query(nativeQuery = true,
            value = "SELECT m.match_started " +
                    "FROM matches m " +
                    "WHERE m.match_id = :matchId ")
    boolean checkStatusStart(@Param("matchId") Integer matchId);

    @Query(nativeQuery = true,
            value = "SELECT m.match_finished " +
                    "FROM matches m " +
                    "WHERE m.match_id = :matchId ")
    boolean checkStatusFinish(@Param("matchId") Integer matchId);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) > 0 " +
                    "FROM classifications_table ct " +
                    "JOIN matches m ON ct.team = m.home_team " +
                    "WHERE ct.team = :teamId " +
                    "AND ct.championship_id = :championshipId ")
    boolean checkIfHomeTeamIsOnChampionship(@Param("teamId") Integer teamId,@Param("championshipId") Integer championshipId);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) > 0 " +
                    "FROM classifications_table ct " +
                    "JOIN matches m ON ct.team = m.visiting_team " +
                    "WHERE ct.team = :teamId " +
                    "AND ct.championship_id = :championshipId ")
    boolean checkIfVisitingTeamIsOnChampionship(@Param("teamId") Integer teamId,@Param("championshipId") Integer championshipId);
}
