package com.cbf.championship.teams.repository;

import com.cbf.championship.teams.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamsRepository extends JpaRepository<Teams, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT count(*) > 0 " +
                    "FROM teams t " +
                    "WHERE t.team_name = :teamName ")
    boolean countTeamsByName(@Param("teamName") String teamName);
}
