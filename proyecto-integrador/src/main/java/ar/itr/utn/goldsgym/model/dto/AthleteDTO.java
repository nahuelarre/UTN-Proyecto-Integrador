package ar.itr.utn.goldsgym.model.dto;

import ar.itr.utn.goldsgym.model.entities.Goal;
import ar.itr.utn.goldsgym.model.entities.Level;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AthleteDTO {
    private Long id;
    private String name;
    private int age;
    private double weight;
    private int height;
    private LocalDate startingDate;
    private Goal goal;
    private Level level;
    private boolean active = true;
    private List<RoutineDTO> routines;
}
