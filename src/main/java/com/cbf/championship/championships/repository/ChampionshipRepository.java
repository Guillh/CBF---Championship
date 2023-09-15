package com.cbf.championship.championships.repository;

import com.cbf.championship.championships.Championships;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionshipRepository extends JpaRepository<Championships, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) > 0 " +
                    "FROM championships c " +
                    "WHERE c.championship_name = :championshipName " +
                    "AND c.championship_date = :championshipDate ")
    boolean countChampionshipsByNameAndDate(@Param("championshipName") String championshipName, @Param("championshipDate") Integer championshipDate);

    @Query(nativeQuery = true,
            value = "select count(ct.team) " +
                    "from classifications_table ct " +
                    "where ct.championship_id  = :championshipId")
    Integer countTeamsByChampionshipId(@Param("championshipId") Integer championshipId);

    @Query(nativeQuery = true,
            value = "select COUNT(*) " +
                    "from matches m " +
                    "where m.championship_id = :championshipId")
    Integer countGamesPlayedByChampionshipId(@Param("championshipId") Integer championshipId);

    @Query(nativeQuery = true,
            value = "SELECT c.championship_started " +
                    "FROM championships c " +
                    "WHERE c.championship_id  = :championshipId ")
    boolean checkChampionshipStartStatus(@Param("championshipId") Integer championshipId);

    @Query(nativeQuery = true,
            value = "SELECT c.championship_finished " +
                    "FROM championships c " +
                    "WHERE c.championship_id  = :championshipId ")
    boolean checkChampionshipFinishStatus(@Param("championshipId") Integer championshipId);
}
