package com.cbf.championship.matches;

import com.cbf.championship.championships.Championships;
import com.cbf.championship.teams.Teams;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "matches")
public class Matches {

    @Id
    @GeneratedValue
    @Column(name = "match_id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "home_team")
    private Teams homeTeam;

    @OneToOne
    @JoinColumn(name = "visiting_team")
    private Teams visitingTeam;

    @Column(name = "home_team_goals")
    private Integer homeTeamGoals;

    @Column(name = "visiting_team_goals")
    private Integer visitingTeamGoals;

    @OneToOne
    @JoinColumn(name = "championship_id")
    private Championships championshipId;

    @Column(name = "match_date")
    private Calendar matchDate;

    @Column(name = "match_started")
    private boolean matchStarted;

    @Column(name = "match_finished")
    private boolean matechFinised;
}
