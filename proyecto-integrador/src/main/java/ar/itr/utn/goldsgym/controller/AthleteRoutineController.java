package ar.itr.utn.goldsgym.controller;

import ar.itr.utn.goldsgym.model.entities.AthleteRoutine;
import ar.itr.utn.goldsgym.usecase.AthleteRoutineUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/athlete-routines")
public class AthleteRoutineController {

    private final AthleteRoutineUseCase athleteRoutineUseCase;

    public AthleteRoutineController(AthleteRoutineUseCase athleteRoutineUseCase) {
        this.athleteRoutineUseCase = athleteRoutineUseCase;
    }

    @PostMapping
    public ResponseEntity<AthleteRoutine> createAthleteRoutine(@RequestBody AthleteRoutine athleteRoutine) {
        AthleteRoutine createdAthleteRoutine = athleteRoutineUseCase.createAthleteRoutine(athleteRoutine);
        return new ResponseEntity<>(createdAthleteRoutine, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AthleteRoutine>> getAllAthleteRoutines() {
        List<AthleteRoutine> athleteRoutines = athleteRoutineUseCase.getAllAthleteRoutines();
        return new ResponseEntity<>(athleteRoutines, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AthleteRoutine> getAthleteRoutineById(@PathVariable Long id) {
        AthleteRoutine athleteRoutine = athleteRoutineUseCase.getAthleteRoutineById(id);
        return athleteRoutine != null ? new ResponseEntity<>(athleteRoutine, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AthleteRoutine> updateAthleteRoutine(@PathVariable Long id, @RequestBody AthleteRoutine athleteRoutineDetails) {
        AthleteRoutine updatedAthleteRoutine = athleteRoutineUseCase.updateAthleteRoutine(id, athleteRoutineDetails);
        return updatedAthleteRoutine != null ? new ResponseEntity<>(updatedAthleteRoutine, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAthleteRoutine(@PathVariable Long id) {
        boolean isDeleted = athleteRoutineUseCase.deleteAthleteRoutine(id);
        return isDeleted ? ResponseEntity.noContent().build() : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

