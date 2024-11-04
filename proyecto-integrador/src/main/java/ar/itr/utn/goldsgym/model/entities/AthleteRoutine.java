package ar.itr.utn.goldsgym.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("athlete_routine")
public class AthleteRoutine {

    @Id
    private Long id;
    private Long athleteId;
    private Long routineId;
}

