package ar.itr.utn.goldsgym.model.repositories;

import ar.itr.utn.goldsgym.model.entities.Athlete;
import org.springframework.data.repository.CrudRepository;

public interface AthleteRepository extends CrudRepository<Athlete, Long> {
}
