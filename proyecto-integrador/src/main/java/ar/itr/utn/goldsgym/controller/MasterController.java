package ar.itr.utn.goldsgym.controller;

import ar.itr.utn.goldsgym.model.dto.AthleteDTO;
import ar.itr.utn.goldsgym.usecase.MasterUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/master")
public class MasterController {

    private final MasterUseCase masterUseCase;

    public MasterController(MasterUseCase masterUseCase) {
        this.masterUseCase = masterUseCase;
    }

    @GetMapping
    public List<AthleteDTO> getAllAthletes() {
        return masterUseCase.getAllAthletes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AthleteDTO> getAthleteById(@PathVariable Long id) {
        AthleteDTO athleteDTO = masterUseCase.getAthleteById(id);
        if (athleteDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(athleteDTO);
    }
}

