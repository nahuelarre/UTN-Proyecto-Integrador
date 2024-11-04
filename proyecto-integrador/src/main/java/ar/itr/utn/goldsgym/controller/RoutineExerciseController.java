package ar.itr.utn.goldsgym.controller;

import ar.itr.utn.goldsgym.model.entities.RoutineExercise;
import ar.itr.utn.goldsgym.usecase.RoutineExerciseUseCase;
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
@RequestMapping("/routine-exercises")
public class RoutineExerciseController {

    private final RoutineExerciseUseCase routineExerciseUseCase;

    public RoutineExerciseController(RoutineExerciseUseCase routineExerciseUseCase) {
        this.routineExerciseUseCase = routineExerciseUseCase;
    }

    @Operation(summary = "Crear un ejercicio de rutina", description = "Crea un nuevo ejercicio dentro de una rutina y lo guarda en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ejercicio de rutina creado exitosamente",
                    content = @Content(schema = @Schema(implementation = RoutineExercise.class)))
    })
    @PostMapping
    public ResponseEntity<RoutineExercise> createRoutineExercise(@RequestBody RoutineExercise routineExercise) {
        RoutineExercise createdRoutineExercise = routineExerciseUseCase.createRoutineExercise(routineExercise);
        return new ResponseEntity<>(createdRoutineExercise, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener un ejercicio de rutina por ID", description = "Devuelve los detalles de un ejercicio de rutina específico por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejercicio de rutina encontrado",
                    content = @Content(schema = @Schema(implementation = RoutineExercise.class))),
            @ApiResponse(responseCode = "404", description = "Ejercicio de rutina no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RoutineExercise> getRoutineExercise(
            @Parameter(description = "ID del ejercicio de rutina a buscar") @PathVariable Long id) {
        RoutineExercise routineExercise = routineExerciseUseCase.getRoutineExerciseById(id);
        return routineExercise != null ? new ResponseEntity<>(routineExercise, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Obtener todos los ejercicios de rutina", description = "Devuelve una lista de todos los ejercicios en todas las rutinas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ejercicios de rutina obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = RoutineExercise.class)))
    })
    @GetMapping
    public ResponseEntity<List<RoutineExercise>> getAllRoutineExercises() {
        List<RoutineExercise> routineExercises = routineExerciseUseCase.getAllRoutineExercises();
        return new ResponseEntity<>(routineExercises, HttpStatus.OK);
    }

    @Operation(summary = "Actualizar un ejercicio de rutina", description = "Actualiza los detalles de un ejercicio de rutina específico por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejercicio de rutina actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = RoutineExercise.class))),
            @ApiResponse(responseCode = "404", description = "Ejercicio de rutina no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RoutineExercise> updateRoutineExercise(
            @Parameter(description = "ID del ejercicio de rutina a actualizar") @PathVariable Long id,
            @RequestBody RoutineExercise routineExerciseDetails) {
        RoutineExercise updatedRoutineExercise = routineExerciseUseCase.updateRoutineExercise(id, routineExerciseDetails);
        return updatedRoutineExercise != null ? new ResponseEntity<>(updatedRoutineExercise, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Eliminar un ejercicio de rutina", description = "Elimina un ejercicio de rutina específico por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ejercicio de rutina eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Ejercicio de rutina no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoutineExercise(
            @Parameter(description = "ID del ejercicio de rutina a eliminar") @PathVariable Long id) {
        boolean isDeleted = routineExerciseUseCase.deleteRoutineExercise(id);
        return isDeleted ? ResponseEntity.noContent().build() : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}


