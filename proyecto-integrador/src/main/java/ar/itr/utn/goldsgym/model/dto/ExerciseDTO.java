package ar.itr.utn.goldsgym.model.dto;

import ar.itr.utn.goldsgym.model.entities.ExerciseList;
import ar.itr.utn.goldsgym.model.entities.MuscleGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDTO {
    private Long id;
    private ExerciseList exerciseList;
    private MuscleGroup muscleGroup;
    private int sets;
    private int repetitions;
    private DayOfWeek dayOfWeek;
}
