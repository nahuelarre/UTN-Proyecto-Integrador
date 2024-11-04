package ar.itr.utn.goldsgym.usecase;

import ar.itr.utn.goldsgym.model.entities.Exercise;
import ar.itr.utn.goldsgym.model.repositories.ExerciseRoutineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseUseCase {
    private final ExerciseRoutineRepository exerciseRoutineRepository;

    public ExerciseUseCase(ExerciseRoutineRepository exerciseRoutineRepository) {
        this.exerciseRoutineRepository = exerciseRoutineRepository;
    }

    public Exercise createExerciseRoutine(Exercise exercise) {
        return exerciseRoutineRepository.save(exercise);
    }

    public Exercise getExerciseRoutine(Long id) {
        return exerciseRoutineRepository.findById(id).orElse(null);
    }

    public List<Exercise> getAllExerciseRoutines() {
        return (List<Exercise>) exerciseRoutineRepository.findAll();
    }

    public Exercise updateExerciseRoutine(Long id, Exercise exerciseDetails) {
        Optional<Exercise> optionalExerciseRoutine = exerciseRoutineRepository.findById(id);

        if (optionalExerciseRoutine.isPresent()) {
            Exercise exercise = optionalExerciseRoutine.get();
            exercise.setExerciseList(exerciseDetails.getExerciseList());
            exercise.setMuscleGroup(exerciseDetails.getMuscleGroup());
            exercise.setSets(exerciseDetails.getSets());
            exercise.setRepetitions(exerciseDetails.getRepetitions());
            exercise.setDayOfWeek(exerciseDetails.getDayOfWeek());

            return exerciseRoutineRepository.save(exercise);
        } else {
            return null;
        }
    }

    public boolean deleteExerciseRoutine(Long id) {
        if (exerciseRoutineRepository.existsById(id)) {
            exerciseRoutineRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

