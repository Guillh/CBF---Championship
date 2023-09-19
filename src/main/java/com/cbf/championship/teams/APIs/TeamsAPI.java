package com.cbf.championship.teams.APIs;

import com.cbf.championship.teams.Teams;
import com.cbf.championship.teams.service.TeamsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/replace/{id}")
    public ResponseEntity<Object> replaceTeam(@PathVariable Integer id, @RequestBody Teams teams){
        return this.teamsService.replaceTeam(id,teams);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteTeam(@PathVariable Integer id){
        return this.teamsService.deleteTeam(id);
    }
}
