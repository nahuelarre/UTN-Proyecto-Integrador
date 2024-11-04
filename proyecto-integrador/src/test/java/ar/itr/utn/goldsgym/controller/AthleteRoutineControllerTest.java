package ar.itr.utn.goldsgym.controller;

import ar.itr.utn.goldsgym.model.entities.AthleteRoutine;
import ar.itr.utn.goldsgym.usecase.AthleteRoutineUseCase;
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

public class AthleteRoutineControllerTest {

    @InjectMocks
    private AthleteRoutineController athleteRoutineController;

    @Mock
    private AthleteRoutineUseCase athleteRoutineUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAthleteRoutine() {
        AthleteRoutine athleteRoutine = new AthleteRoutine(null, 1L, 2L);
        AthleteRoutine createdAthleteRoutine = new AthleteRoutine(1L, 1L, 2L);

        when(athleteRoutineUseCase.createAthleteRoutine(any(AthleteRoutine.class))).thenReturn(createdAthleteRoutine);

        ResponseEntity<AthleteRoutine> response = athleteRoutineController.createAthleteRoutine(athleteRoutine);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals(1L, response.getBody().getAthleteId());
        assertEquals(2L, response.getBody().getRoutineId());
    }

    @Test
    public void testGetAllAthleteRoutines() {
        AthleteRoutine athleteRoutine1 = new AthleteRoutine(1L, 1L, 2L);
        AthleteRoutine athleteRoutine2 = new AthleteRoutine(2L, 2L, 3L);
        List<AthleteRoutine> athleteRoutines = Arrays.asList(athleteRoutine1, athleteRoutine2);

        when(athleteRoutineUseCase.getAllAthleteRoutines()).thenReturn(athleteRoutines);

        ResponseEntity<List<AthleteRoutine>> response = athleteRoutineController.getAllAthleteRoutines();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetAthleteRoutineById() {
        AthleteRoutine athleteRoutine = new AthleteRoutine(1L, 1L, 2L);
        when(athleteRoutineUseCase.getAthleteRoutineById(1L)).thenReturn(athleteRoutine);

        ResponseEntity<AthleteRoutine> response = athleteRoutineController.getAthleteRoutineById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testGetAthleteRoutineById_NotFound() {
        when(athleteRoutineUseCase.getAthleteRoutineById(1L)).thenReturn(null);

        ResponseEntity<AthleteRoutine> response = athleteRoutineController.getAthleteRoutineById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateAthleteRoutine() {
        AthleteRoutine existingRoutine = new AthleteRoutine(1L, 1L, 2L);
        AthleteRoutine updatedDetails = new AthleteRoutine(null, 1L, 3L);
        when(athleteRoutineUseCase.updateAthleteRoutine(anyLong(), any(AthleteRoutine.class))).thenReturn(new AthleteRoutine(1L, 1L, 3L));

        ResponseEntity<AthleteRoutine> response = athleteRoutineController.updateAthleteRoutine(1L, updatedDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals(3L, response.getBody().getRoutineId());
    }

    @Test
    public void testUpdateAthleteRoutine_NotFound() {
        AthleteRoutine updatedDetails = new AthleteRoutine(null, 1L, 3L);
        when(athleteRoutineUseCase.updateAthleteRoutine(anyLong(), any(AthleteRoutine.class))).thenReturn(null);

        ResponseEntity<AthleteRoutine> response = athleteRoutineController.updateAthleteRoutine(1L, updatedDetails);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteAthleteRoutine() {
        when(athleteRoutineUseCase.deleteAthleteRoutine(1L)).thenReturn(true);

        ResponseEntity<Void> response = athleteRoutineController.deleteAthleteRoutine(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteAthleteRoutine_NotFound() {
        when(athleteRoutineUseCase.deleteAthleteRoutine(1L)).thenReturn(false);

        ResponseEntity<Void> response = athleteRoutineController.deleteAthleteRoutine(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

