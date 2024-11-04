package ar.itr.utn.goldsgym.model.repositories;

import ar.itr.utn.goldsgym.model.entities.Routine;
import org.springframework.data.repository.CrudRepository;

public interface RoutineRepository extends CrudRepository<Routine, Long> {
}
