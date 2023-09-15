package com.cbf.championship.teams;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "teams")
public class Teams {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Integer id;

    @Column(name = "team_name")
    private String name;
}
