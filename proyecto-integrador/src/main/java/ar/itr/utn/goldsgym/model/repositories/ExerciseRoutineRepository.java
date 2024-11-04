package ar.itr.utn.goldsgym.model.repositories;

import ar.itr.utn.goldsgym.model.entities.Exercise;
import org.springframework.data.repository.CrudRepository;

public interface ExerciseRoutineRepository extends CrudRepository<Exercise, Long> {
}
