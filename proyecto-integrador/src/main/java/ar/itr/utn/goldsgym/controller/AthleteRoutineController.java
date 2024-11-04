package ar.itr.utn.goldsgym.controller;

import ar.itr.utn.goldsgym.model.entities.AthleteRoutine;
import ar.itr.utn.goldsgym.usecase.AthleteRoutineUseCase;
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
@RequestMapping("/athlete-routines")
public class AthleteRoutineController {

    private final AthleteRoutineUseCase athleteRoutineUseCase;

    public AthleteRoutineController(AthleteRoutineUseCase athleteRoutineUseCase) {
        this.athleteRoutineUseCase = athleteRoutineUseCase;
    }

    @Operation(summary = "Crear una nueva rutina de atleta", description = "Crea y guarda una nueva rutina de atleta en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rutina de atleta creada satisfactoriamente",
                    content = @Content(schema = @Schema(implementation = AthleteRoutine.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<AthleteRoutine> createAthleteRoutine(@RequestBody AthleteRoutine athleteRoutine) {
        AthleteRoutine createdAthleteRoutine = athleteRoutineUseCase.createAthleteRoutine(athleteRoutine);
        return new ResponseEntity<>(createdAthleteRoutine, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todas las rutinas de atletas", description = "Devuelve una lista de todas las rutinas de atletas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de rutinas de atletas",
                    content = @Content(schema = @Schema(implementation = AthleteRoutine.class)))
    })
    @GetMapping
    public ResponseEntity<List<AthleteRoutine>> getAllAthleteRoutines() {
        List<AthleteRoutine> athleteRoutines = athleteRoutineUseCase.getAllAthleteRoutines();
        return new ResponseEntity<>(athleteRoutines, HttpStatus.OK);
    }

    @Operation(summary = "Obtener una rutina de atleta por ID", description = "Devuelve una rutina de atleta específica por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rutina de atleta encontrada",
                    content = @Content(schema = @Schema(implementation = AthleteRoutine.class))),
            @ApiResponse(responseCode = "404", description = "Rutina de atleta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AthleteRoutine> getAthleteRoutineById(
            @Parameter(description = "ID de la rutina de atleta a buscar") @PathVariable Long id) {
        AthleteRoutine athleteRoutine = athleteRoutineUseCase.getAthleteRoutineById(id);
        return athleteRoutine != null ? new ResponseEntity<>(athleteRoutine, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Actualizar una rutina de atleta", description = "Actualiza los detalles de una rutina de atleta existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rutina de atleta actualizada satisfactoriamente",
                    content = @Content(schema = @Schema(implementation = AthleteRoutine.class))),
            @ApiResponse(responseCode = "404", description = "Rutina de atleta no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AthleteRoutine> updateAthleteRoutine(
            @Parameter(description = "ID de la rutina de atleta a actualizar") @PathVariable Long id,
            @RequestBody AthleteRoutine athleteRoutineDetails) {
        AthleteRoutine updatedAthleteRoutine = athleteRoutineUseCase.updateAthleteRoutine(id, athleteRoutineDetails);
        return updatedAthleteRoutine != null ? new ResponseEntity<>(updatedAthleteRoutine, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Eliminar una rutina de atleta", description = "Elimina una rutina de atleta específica por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rutina de atleta eliminada satisfactoriamente"),
            @ApiResponse(responseCode = "404", description = "Rutina de atleta no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAthleteRoutine(
            @Parameter(description = "ID de la rutina de atleta a eliminar") @PathVariable Long id) {
        boolean isDeleted = athleteRoutineUseCase.deleteAthleteRoutine(id);
        return isDeleted ? ResponseEntity.noContent().build() : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}


