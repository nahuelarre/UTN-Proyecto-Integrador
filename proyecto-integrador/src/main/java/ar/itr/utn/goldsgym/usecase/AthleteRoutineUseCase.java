package ar.itr.utn.goldsgym.usecase;

import ar.itr.utn.goldsgym.model.entities.AthleteRoutine;
import ar.itr.utn.goldsgym.model.repositories.AthleteRoutineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AthleteRoutineUseCase {
    private final AthleteRoutineRepository athleteRoutineRepository;

    public AthleteRoutineUseCase(AthleteRoutineRepository athleteRoutineRepository) {
        this.athleteRoutineRepository = athleteRoutineRepository;
    }

    public AthleteRoutine createAthleteRoutine(AthleteRoutine athleteRoutine) {
        return athleteRoutineRepository.save(athleteRoutine);
    }

    public AthleteRoutine getAthleteRoutineById(Long id) {
        return athleteRoutineRepository.findById(id).orElse(null);
    }

    public List<AthleteRoutine> getAllAthleteRoutines() {
        return (List<AthleteRoutine>) athleteRoutineRepository.findAll();
    }

    public AthleteRoutine updateAthleteRoutine(Long id, AthleteRoutine athleteRoutineDetails) {
        Optional<AthleteRoutine> optionalAthleteRoutine = athleteRoutineRepository.findById(id);

        if (optionalAthleteRoutine.isPresent()) {
            AthleteRoutine athleteRoutine = optionalAthleteRoutine.get();
            athleteRoutine.setAthleteId(athleteRoutineDetails.getAthleteId());
            athleteRoutine.setRoutineId(athleteRoutineDetails.getRoutineId());

            return athleteRoutineRepository.save(athleteRoutine);
        } else {
            return null;
        }
    }

    public boolean deleteAthleteRoutine(Long id) {
        if (athleteRoutineRepository.existsById(id)) {
            athleteRoutineRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}


