package com.cbf.championship.teams.repository;

import com.cbf.championship.teams.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamsRepository extends JpaRepository<Teams, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) > 0 " +
                    "FROM teams t " +
                    "WHERE t.team_name = :teamName ")
    boolean countTeamsByName(@Param("teamName") String teamName);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(ct.team) > 0 " +
                    "FROM classifications_table ct " +
                    "WHERE ct.team = :teamId ")
    boolean countTeamsInChampionship(@Param("teamId") Integer teamId);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(m.home_team) > 0 OR COUNT(m.visiting_team) > 0 " +
                    "FROM matches m " +
                    "WHERE m.home_team = :teamId " +
                    "OR m.visiting_team = :teamId ")
    boolean countTeamsInMateches(@Param("teamId") Integer teamId);
}
