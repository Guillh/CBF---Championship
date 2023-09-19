package com.cbf.championship.Dto.matechesDto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@NoArgsConstructor
public class MatchesDto {

    @NotNull
    private Integer homeTeam;
    @NotNull
    private Integer visitingTeam;

    private Integer championshipId;
    @NotNull
    private Calendar matchDate;



}
