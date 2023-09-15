package com.cbf.championship.matches.APIs;

import com.cbf.championship.Dto.matechesDto.MatchesDto;
import com.cbf.championship.matches.service.MatchesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matches")
public class MatchesAPI {
    private final MatchesService matchesService;

    public MatchesAPI(MatchesService matchesService) {
        this.matchesService = matchesService;
    }
    @PostMapping("/create")
    public ResponseEntity<Object> createMatch(@RequestBody MatchesDto matchesDto){
        return this.matchesService.createMatch(matchesDto);
    }
    @PutMapping("/start-match/{matchId}")
    public ResponseEntity<Object> startMatch(@PathVariable Integer matchId) {
        return this.matchesService.startMatch(matchId);
    }
    @PutMapping("/finish-match/{matchId}")
    public ResponseEntity<Object> finishMatch(@PathVariable Integer matchId) {
        return this.matchesService.finishMatch(matchId);
    }
}
