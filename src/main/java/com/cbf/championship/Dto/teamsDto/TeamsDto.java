package com.cbf.championship.Dto.teamsDto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class TeamsDto {
    @NotNull
    private ArrayList<Integer> teams = new ArrayList<>();
    @NotNull
    private Integer championshipId;
}
