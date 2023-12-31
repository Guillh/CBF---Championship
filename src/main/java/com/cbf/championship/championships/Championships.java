package com.cbf.championship.championships;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "championships")
public class Championships {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "championship_id")
    private Integer id;

    @Column(name = "championship_name")
    private String championshipName;

    @Column(name = "championship_date")
    private Integer championshipDate;

    @Column(name = "championship_started")
    private boolean championshipStarted;

    @Column(name = "championship_finished")
    private boolean championshipFinished;


}
