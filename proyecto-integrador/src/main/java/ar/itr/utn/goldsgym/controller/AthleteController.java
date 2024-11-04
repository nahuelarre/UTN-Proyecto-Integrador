package ar.itr.utn.goldsgym.controller;

import ar.itr.utn.goldsgym.model.entities.Athlete;
import ar.itr.utn.goldsgym.usecase.AthleteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(summary = "Crear un nuevo atleta", description = "Crea y guarda un nuevo atleta en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Atleta creado satisfactoriamente",
                    content = @Content(schema = @Schema(implementation = Athlete.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<Athlete> createAthlete(@RequestBody Athlete athlete) {
        Athlete createdAthlete = athleteUseCase.createAthlete(athlete);
        return new ResponseEntity<>(createdAthlete, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener un atleta por ID", description = "Devuelve un atleta específico por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atleta encontrado",
                    content = @Content(schema = @Schema(implementation = Athlete.class))),
            @ApiResponse(responseCode = "404", description = "Atleta no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Athlete> getAthlete(
            @Parameter(description = "ID del atleta a buscar") @PathVariable Long id) {
        Athlete athlete = athleteUseCase.getAthlete(id);
        return athlete != null ? new ResponseEntity<>(athlete, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Obtener todos los atletas", description = "Devuelve una lista de todos los atletas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de atletas",
                    content = @Content(schema = @Schema(implementation = Athlete.class)))
    })
    @GetMapping
    public ResponseEntity<List<Athlete>> getAllAthletes() {
        List<Athlete> athletes = athleteUseCase.getAllAthletes();
        return new ResponseEntity<>(athletes, HttpStatus.OK);
    }

    @Operation(summary = "Actualizar un atleta", description = "Actualiza los detalles de un atleta existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atleta actualizado satisfactoriamente",
                    content = @Content(schema = @Schema(implementation = Athlete.class))),
            @ApiResponse(responseCode = "404", description = "Atleta no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Athlete> updateAthlete(
            @Parameter(description = "ID del atleta a actualizar") @PathVariable Long id,
            @RequestBody Athlete athleteDetails) {
        Athlete updatedAthlete = athleteUseCase.updateAthlete(id, athleteDetails);
        return updatedAthlete != null ? new ResponseEntity<>(updatedAthlete, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Eliminar un atleta", description = "Elimina un atleta específico por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Atleta eliminado satisfactoriamente"),
            @ApiResponse(responseCode = "404", description = "Atleta no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAthlete(
            @Parameter(description = "ID del atleta a eliminar") @PathVariable Long id) {
        boolean deleted = athleteUseCase.deleteAthlete(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
