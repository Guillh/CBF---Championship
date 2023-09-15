package com.cbf.championship.classifications_table;

import com.cbf.championship.championships.Championships;
import com.cbf.championship.teams.Teams;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "classifications_table")
public class ClassificationsTable {

    @Id
    @GeneratedValue
    @Column(name = "classification_table_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "championship_id")
    private Championships championshipId;

    @ManyToOne
    @JoinColumn(name = "team")
    private Teams team;

    @Column(name = "classification_table_points")
    private Integer points;

    @Column(name = "classification_table_wins")
    private Integer win;

    @Column(name = "classification_table_draws")
    private Integer draw;

    @Column(name = "classification_table_looses")
    private Integer loss;

    @Column(name = "classification_table_goals_scored")
    private Integer goalsScored;

    @Column(name = "classification_table_goals_conceded")
    private Integer goalsConceded;

    @Column(name = "classification_table_goals_difference")
    private Integer goalsDifference;
}
