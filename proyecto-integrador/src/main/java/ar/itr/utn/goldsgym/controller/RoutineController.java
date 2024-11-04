package ar.itr.utn.goldsgym.controller;

import ar.itr.utn.goldsgym.model.entities.Routine;
import ar.itr.utn.goldsgym.usecase.RoutineUseCase;
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
@RequestMapping("/routines")
public class RoutineController {

    private final RoutineUseCase routineUseCase;

    public RoutineController(RoutineUseCase routineUseCase) {
        this.routineUseCase = routineUseCase;
    }

    @Operation(summary = "Crear una rutina", description = "Crea una nueva rutina y la guarda en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rutina creada exitosamente",
                    content = @Content(schema = @Schema(implementation = Routine.class)))
    })
    @PostMapping
    public ResponseEntity<Routine> createRoutine(@RequestBody Routine routine) {
        Routine createdRoutine = routineUseCase.createRoutine(routine);
        return new ResponseEntity<>(createdRoutine, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener una rutina por ID", description = "Devuelve los detalles de una rutina específica por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rutina encontrada",
                    content = @Content(schema = @Schema(implementation = Routine.class))),
            @ApiResponse(responseCode = "404", description = "Rutina no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Routine> getRoutine(
            @Parameter(description = "ID de la rutina a buscar") @PathVariable Long id) {
        Routine routine = routineUseCase.getRoutine(id);
        return routine != null ? new ResponseEntity<>(routine, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Obtener todas las rutinas", description = "Devuelve una lista de todas las rutinas registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de rutinas obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Routine.class)))
    })
    @GetMapping
    public ResponseEntity<List<Routine>> getAllRoutines() {
        List<Routine> routines = routineUseCase.getAllRoutines();
        return new ResponseEntity<>(routines, HttpStatus.OK);
    }

    @Operation(summary = "Actualizar una rutina", description = "Actualiza los detalles de una rutina específica por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rutina actualizada exitosamente",
                    content = @Content(schema = @Schema(implementation = Routine.class))),
            @ApiResponse(responseCode = "404", description = "Rutina no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Routine> updateRoutine(
            @Parameter(description = "ID de la rutina a actualizar") @PathVariable Long id,
            @RequestBody Routine routineDetails) {
        Routine updatedRoutine = routineUseCase.updateRoutine(id, routineDetails);
        return updatedRoutine != null ? new ResponseEntity<>(updatedRoutine, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Eliminar una rutina", description = "Elimina una rutina específica por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rutina eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Rutina no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoutine(
            @Parameter(description = "ID de la rutina a eliminar") @PathVariable Long id) {
        boolean isDeleted = routineUseCase.deleteRoutine(id);
        return isDeleted ? ResponseEntity.noContent().build() : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}


