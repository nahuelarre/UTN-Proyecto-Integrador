package ar.itr.utn.goldsgym.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("routine_exercise")
public class RoutineExercise {

    @Id
    private Long id;
    private Long routineId;
    private Long exerciseId;
}

