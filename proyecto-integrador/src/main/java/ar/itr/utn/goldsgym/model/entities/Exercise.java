package ar.itr.utn.goldsgym.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.DayOfWeek;

@Data
@Table(name="exercise")
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    @Id
    private Long id;
    private ExerciseList exerciseList;
    private MuscleGroup muscleGroup;
    private int sets;
    private int repetitions;
    private DayOfWeek dayOfWeek;
}
