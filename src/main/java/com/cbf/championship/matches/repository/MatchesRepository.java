package com.cbf.championship.matches.repository;

import com.cbf.championship.matches.Matches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
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
            value = "select m.championship_id " +
                    "from matches m " +
                    "where m.match_id = :matchId ")
    Integer findChampionshipIdByMatchId(@Param("matchId") Integer matchId);



    @Query(nativeQuery = true,
            value = "select m.home_team_goals " +
                    "from matches m " +
                    "where m.match_id = :matchId ")
    Integer getGoalsOfHomeTeam(@Param("matchId") Integer matchId);

    @Query(nativeQuery = true,
            value = "select m.visiting_team_goals " +
                    "from matches m " +
                    "where m.match_id = :matchId ")
    Integer getGoalsOfVisitingTeam(@Param("matchId") Integer matchId);

    @Query(nativeQuery = true,
            value = "select ct.classification_table_id " +
                    "from classifications_table ct " +
                    "where ct.championship_id = :championshipId " +
                    "and ct.team = :teamId ")
    Integer findClassificationTableIdByTeamIdAndChampionshipId(@Param("championshipId") Integer championshipId, @Param("teamId") Integer teamId);
}
