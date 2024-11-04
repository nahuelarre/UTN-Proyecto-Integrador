package ar.itr.utn.goldsgym.controller;

import ar.itr.utn.goldsgym.model.entities.Athlete;
import ar.itr.utn.goldsgym.usecase.AthleteUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/athletes")
public class AthleteController {
    private final AthleteUseCase athleteUseCase;

    public AthleteController(AthleteUseCase athleteUseCase) {
        this.athleteUseCase = athleteUseCase;
    }

    @PostMapping
    public ResponseEntity<Athlete> createAthlete(@RequestBody Athlete athlete) {
        Athlete createdAthlete = athleteUseCase.createAthlete(athlete);
        return new ResponseEntity<>(createdAthlete, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Athlete> getAthlete(@PathVariable Long id) {
        Athlete athlete = athleteUseCase.getAthlete(id);
        return athlete != null ? new ResponseEntity<>(athlete, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Athlete>> getAllAthletes() {
        List<Athlete> athletes = athleteUseCase.getAllAthletes();
        return new ResponseEntity<>(athletes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Athlete> updateAthlete(@PathVariable Long id, @RequestBody Athlete athleteDetails) {
        Athlete updatedAthlete = athleteUseCase.updateAthlete(id, athleteDetails);
        return updatedAthlete != null ? new ResponseEntity<>(updatedAthlete, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAthlete(@PathVariable Long id) {
        boolean deleted = athleteUseCase.deleteAthlete(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
