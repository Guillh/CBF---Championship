package com.cbf.championship.teams.APIs;

import com.cbf.championship.teams.Teams;
import com.cbf.championship.teams.service.TeamsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teams")
public class TeamsAPI {
    private final TeamsService teamsService;

    public TeamsAPI(TeamsService teamsService) {
        this.teamsService = teamsService;
    }

    @PostMapping("/create")
    public ResponseEntity createTeam(@RequestBody Teams teams) {
        return ResponseEntity.ok(this.teamsService.createTeam(teams));
    }
}
