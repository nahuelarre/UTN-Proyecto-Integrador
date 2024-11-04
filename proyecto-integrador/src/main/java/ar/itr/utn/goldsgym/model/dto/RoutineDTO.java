package ar.itr.utn.goldsgym.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineDTO {
    private Long id;
    private String name;
    private String description;
    private int duration;
    private int weeklyFrequency;
    private boolean active;
    private List<ExerciseDTO> exercices;
}
