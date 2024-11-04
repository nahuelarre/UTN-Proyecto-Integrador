package ar.itr.utn.goldsgym.usecase;

import ar.itr.utn.goldsgym.model.entities.RoutineExercise;
import ar.itr.utn.goldsgym.model.repositories.RoutineExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoutineExerciseUseCase {

    private final RoutineExerciseRepository routineExerciseRepository;

    public RoutineExerciseUseCase(RoutineExerciseRepository routineExerciseRepository) {
        this.routineExerciseRepository = routineExerciseRepository;
    }

    public RoutineExercise createRoutineExercise(RoutineExercise routineExercise) {
        return routineExerciseRepository.save(routineExercise);
    }

    public RoutineExercise getRoutineExerciseById(Long id) {
        return routineExerciseRepository.findById(id).orElse(null);
    }

    public List<RoutineExercise> getAllRoutineExercises() {
        return (List<RoutineExercise>) routineExerciseRepository.findAll();
    }

    public RoutineExercise updateRoutineExercise(Long id, RoutineExercise routineExerciseDetails) {
        Optional<RoutineExercise> optionalRoutineExercise = routineExerciseRepository.findById(id);

        if (optionalRoutineExercise.isPresent()) {
            RoutineExercise routineExercise = optionalRoutineExercise.get();
            routineExercise.setRoutineId(routineExerciseDetails.getRoutineId());
            routineExercise.setExerciseId(routineExerciseDetails.getExerciseId());

            return routineExerciseRepository.save(routineExercise);
        } else {
            return null;
        }
    }

    public boolean deleteRoutineExercise(Long id) {
        if (routineExerciseRepository.existsById(id)) {
            routineExerciseRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

