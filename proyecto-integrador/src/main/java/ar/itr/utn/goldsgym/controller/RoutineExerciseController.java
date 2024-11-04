package ar.itr.utn.goldsgym.controller;

import ar.itr.utn.goldsgym.model.entities.RoutineExercise;
import ar.itr.utn.goldsgym.usecase.RoutineExerciseUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routine-exercises")
public class RoutineExerciseController {

    private final RoutineExerciseUseCase routineExerciseUseCase;

    public RoutineExerciseController(RoutineExerciseUseCase routineExerciseUseCase) {
        this.routineExerciseUseCase = routineExerciseUseCase;
    }

    @PostMapping
    public ResponseEntity<RoutineExercise> createRoutineExercise(@RequestBody RoutineExercise routineExercise) {
        RoutineExercise createdRoutineExercise = routineExerciseUseCase.createRoutineExercise(routineExercise);
        return new ResponseEntity<>(createdRoutineExercise, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoutineExercise> getRoutineExercise(@PathVariable Long id) {
        RoutineExercise routineExercise = routineExerciseUseCase.getRoutineExerciseById(id);
        return routineExercise != null ? new ResponseEntity<>(routineExercise, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<RoutineExercise>> getAllRoutineExercises() {
        List<RoutineExercise> routineExercises = routineExerciseUseCase.getAllRoutineExercises();
        return new ResponseEntity<>(routineExercises, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoutineExercise> updateRoutineExercise(@PathVariable Long id, @RequestBody RoutineExercise routineExerciseDetails) {
        RoutineExercise updatedRoutineExercise = routineExerciseUseCase.updateRoutineExercise(id, routineExerciseDetails);
        return updatedRoutineExercise != null ? new ResponseEntity<>(updatedRoutineExercise, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoutineExercise(@PathVariable Long id) {
        boolean isDeleted = routineExerciseUseCase.deleteRoutineExercise(id);
        return isDeleted ? ResponseEntity.noContent().build() : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

