package ar.itr.utn.goldsgym.controller;

import ar.itr.utn.goldsgym.model.entities.Exercise;
import ar.itr.utn.goldsgym.usecase.ExerciseUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercise-routines")
public class ExerciseController {

    private final ExerciseUseCase exerciseUseCase;

    public ExerciseController(ExerciseUseCase exerciseUseCase) {
        this.exerciseUseCase = exerciseUseCase;
    }

    @PostMapping
    public ResponseEntity<Exercise> createExerciseRoutine(@RequestBody Exercise exercise) {
        Exercise createdExercise = exerciseUseCase.createExerciseRoutine(exercise);
        return new ResponseEntity<>(createdExercise, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseRoutine(@PathVariable Long id) {
        Exercise exercise = exerciseUseCase.getExerciseRoutine(id);
        return exercise != null ? new ResponseEntity<>(exercise, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExerciseRoutines() {
        List<Exercise> exercises = exerciseUseCase.getAllExerciseRoutines();
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exercise> updateExerciseRoutine(@PathVariable Long id, @RequestBody Exercise exerciseDetails) {
        Exercise updatedExercise = exerciseUseCase.updateExerciseRoutine(id, exerciseDetails);
        return updatedExercise != null ? new ResponseEntity<>(updatedExercise, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExerciseRoutine(@PathVariable Long id) {
        boolean isDeleted = exerciseUseCase.deleteExerciseRoutine(id);
        return isDeleted ? ResponseEntity.noContent().build() : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}