package ar.itr.utn.goldsgym.controller;

import ar.itr.utn.goldsgym.model.entities.Routine;
import ar.itr.utn.goldsgym.usecase.RoutineUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routines")
public class RoutineController {

    private final RoutineUseCase routineUseCase;

    public RoutineController(RoutineUseCase routineUseCase) {
        this.routineUseCase = routineUseCase;
    }

    @PostMapping
    public ResponseEntity<Routine> createRoutine(@RequestBody Routine routine) {
        Routine createdRoutine = routineUseCase.createRoutine(routine);
        return new ResponseEntity<>(createdRoutine, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Routine> getRoutine(@PathVariable Long id) {
        Routine routine = routineUseCase.getRoutine(id);
        return routine != null ? new ResponseEntity<>(routine, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Routine>> getAllRoutines() {
        List<Routine> routines = routineUseCase.getAllRoutines();
        return new ResponseEntity<>(routines, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Routine> updateRoutine(@PathVariable Long id, @RequestBody Routine routineDetails) {
        Routine updatedRoutine = routineUseCase.updateRoutine(id, routineDetails);
        return updatedRoutine != null ? new ResponseEntity<>(updatedRoutine, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoutine(@PathVariable Long id) {
        boolean isDeleted = routineUseCase.deleteRoutine(id);
        return isDeleted ? ResponseEntity.noContent().build() : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

