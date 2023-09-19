package com.cbf.championship.championships.APIs;


import com.cbf.championship.Dto.teamsDto.TeamsDto;
import com.cbf.championship.championships.Championships;
import com.cbf.championship.championships.service.ChampionshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/championships")
public class ChampionshipsAPI {
    private final ChampionshipService championshipService;


    public ChampionshipsAPI(ChampionshipService championshipService) {
        this.championshipService = championshipService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createChampionship(@RequestBody Championships championships){
        return ResponseEntity.ok(this.championshipService.createChampionship(championships));
    }

    @PutMapping("/start-championship")
    public ResponseEntity<Object> startChampionship(@RequestBody TeamsDto teamsDto) {
        return ResponseEntity.ok(this.championshipService.startChampioship(teamsDto));
    }

    @PutMapping("/finish-championship")
    public ResponseEntity<Object> finishChampionship(@RequestBody TeamsDto teamsDto) {
        return ResponseEntity.ok(this.championshipService.finishChampionship(teamsDto));
    }
    @PutMapping("/replace/{id}")
    public ResponseEntity<Object> replaceChampionship(@PathVariable Integer id, @RequestBody Championships championships){
        return ResponseEntity.ok(this.championshipService.replaceChampionship(id, championships));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteChampionship(@PathVariable Integer id) {
        return ResponseEntity.ok(this.championshipService.deleteChampionship(id));
    }
}
