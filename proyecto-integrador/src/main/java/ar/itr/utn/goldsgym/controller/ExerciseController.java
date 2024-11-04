package ar.itr.utn.goldsgym.controller;

import ar.itr.utn.goldsgym.model.entities.Exercise;
import ar.itr.utn.goldsgym.usecase.ExerciseUseCase;
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
@RequestMapping("/exercise-routines")
public class ExerciseController {

    private final ExerciseUseCase exerciseUseCase;

    public ExerciseController(ExerciseUseCase exerciseUseCase) {
        this.exerciseUseCase = exerciseUseCase;
    }

    @Operation(summary = "Crear una nueva rutina de ejercicio", description = "Crea y guarda una nueva rutina de ejercicio en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rutina de ejercicio creada satisfactoriamente",
                    content = @Content(schema = @Schema(implementation = Exercise.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<Exercise> createExerciseRoutine(@RequestBody Exercise exercise) {
        Exercise createdExercise = exerciseUseCase.createExerciseRoutine(exercise);
        return new ResponseEntity<>(createdExercise, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener una rutina de ejercicio por ID", description = "Devuelve una rutina de ejercicio específica por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rutina de ejercicio encontrada",
                    content = @Content(schema = @Schema(implementation = Exercise.class))),
            @ApiResponse(responseCode = "404", description = "Rutina de ejercicio no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseRoutine(
            @Parameter(description = "ID de la rutina de ejercicio a buscar") @PathVariable Long id) {
        Exercise exercise = exerciseUseCase.getExerciseRoutine(id);
        return exercise != null ? new ResponseEntity<>(exercise, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Obtener todas las rutinas de ejercicio", description = "Devuelve una lista de todas las rutinas de ejercicio.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de rutinas de ejercicio",
                    content = @Content(schema = @Schema(implementation = Exercise.class)))
    })
    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExerciseRoutines() {
        List<Exercise> exercises = exerciseUseCase.getAllExerciseRoutines();
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    @Operation(summary = "Actualizar una rutina de ejercicio", description = "Actualiza los detalles de una rutina de ejercicio existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rutina de ejercicio actualizada satisfactoriamente",
                    content = @Content(schema = @Schema(implementation = Exercise.class))),
            @ApiResponse(responseCode = "404", description = "Rutina de ejercicio no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Exercise> updateExerciseRoutine(
            @Parameter(description = "ID de la rutina de ejercicio a actualizar") @PathVariable Long id,
            @RequestBody Exercise exerciseDetails) {
        Exercise updatedExercise = exerciseUseCase.updateExerciseRoutine(id, exerciseDetails);
        return updatedExercise != null ? new ResponseEntity<>(updatedExercise, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Eliminar una rutina de ejercicio", description = "Elimina una rutina de ejercicio específica por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rutina de ejercicio eliminada satisfactoriamente"),
            @ApiResponse(responseCode = "404", description = "Rutina de ejercicio no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExerciseRoutine(
            @Parameter(description = "ID de la rutina de ejercicio a eliminar") @PathVariable Long id) {
        boolean isDeleted = exerciseUseCase.deleteExerciseRoutine(id);
        return isDeleted ? ResponseEntity.noContent().build() : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
