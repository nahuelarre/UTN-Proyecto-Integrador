package ar.itr.utn.goldsgym.controller;

import ar.itr.utn.goldsgym.model.entities.RoutineExercise;
import ar.itr.utn.goldsgym.usecase.RoutineExerciseUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RoutineExerciseControllerTest {

    @InjectMocks
    private RoutineExerciseController routineExerciseController;

    @Mock
    private RoutineExerciseUseCase routineExerciseUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateRoutineExercise() {
        RoutineExercise routineExercise = new RoutineExercise(null, 1L, 2L);
        RoutineExercise createdRoutineExercise = new RoutineExercise(1L, 1L, 2L);

        when(routineExerciseUseCase.createRoutineExercise(any(RoutineExercise.class))).thenReturn(createdRoutineExercise);

        ResponseEntity<RoutineExercise> response = routineExerciseController.createRoutineExercise(routineExercise);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testGetRoutineExercise() {
        RoutineExercise routineExercise = new RoutineExercise(1L, 1L, 2L);

        when(routineExerciseUseCase.getRoutineExerciseById(1L)).thenReturn(routineExercise);

        ResponseEntity<RoutineExercise> response = routineExerciseController.getRoutineExercise(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testGetRoutineExercise_NotFound() {
        when(routineExerciseUseCase.getRoutineExerciseById(anyLong())).thenReturn(null);

        ResponseEntity<RoutineExercise> response = routineExerciseController.getRoutineExercise(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAllRoutineExercises() {
        RoutineExercise routineExercise1 = new RoutineExercise(1L, 1L, 2L);
        RoutineExercise routineExercise2 = new RoutineExercise(2L, 2L, 3L);

        when(routineExerciseUseCase.getAllRoutineExercises()).thenReturn(Arrays.asList(routineExercise1, routineExercise2));

        ResponseEntity<List<RoutineExercise>> response = routineExerciseController.getAllRoutineExercises();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testUpdateRoutineExercise() {
        RoutineExercise existingRoutineExercise = new RoutineExercise(1L, 1L, 2L);
        RoutineExercise updatedDetails = new RoutineExercise(null, 1L, 3L); // Aquí 3L es el nuevo routineId

        // Simular el comportamiento del caso de uso
        when(routineExerciseUseCase.updateRoutineExercise(1L, updatedDetails))
                .thenReturn(new RoutineExercise(1L, 3L, 2L)); // 1L como ID, 3L como nuevo routineId

        // Llamar al controlador
        ResponseEntity<RoutineExercise> response = routineExerciseController.updateRoutineExercise(1L, updatedDetails);

        // Verificar resultados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3L, response.getBody().getRoutineId()); // Aquí verificamos que se haya actualizado correctamente
    }

    @Test
    public void testUpdateRoutineExercise_NotFound() {
        RoutineExercise updatedDetails = new RoutineExercise(null, 1L, 3L);

        when(routineExerciseUseCase.updateRoutineExercise(anyLong(), any(RoutineExercise.class))).thenReturn(null);

        ResponseEntity<RoutineExercise> response = routineExerciseController.updateRoutineExercise(1L, updatedDetails);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteRoutineExercise() {
        when(routineExerciseUseCase.deleteRoutineExercise(1L)).thenReturn(true);

        ResponseEntity<Void> response = routineExerciseController.deleteRoutineExercise(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteRoutineExercise_NotFound() {
        when(routineExerciseUseCase.deleteRoutineExercise(1L)).thenReturn(false);

        ResponseEntity<Void> response = routineExerciseController.deleteRoutineExercise(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

