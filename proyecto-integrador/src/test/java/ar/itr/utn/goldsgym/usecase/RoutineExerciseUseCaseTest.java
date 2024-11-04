package ar.itr.utn.goldsgym.usecase;

import ar.itr.utn.goldsgym.model.entities.RoutineExercise;
import ar.itr.utn.goldsgym.model.repositories.RoutineExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RoutineExerciseUseCaseTest {

    private RoutineExerciseRepository routineExerciseRepository;
    private RoutineExerciseUseCase routineExerciseUseCase;

    @BeforeEach
    void setUp() {
        routineExerciseRepository = mock(RoutineExerciseRepository.class);
        routineExerciseUseCase = new RoutineExerciseUseCase(routineExerciseRepository);
    }

    @Test
    void testCreateRoutineExercise() {
        RoutineExercise exercise = new RoutineExercise(1L, 1L, 1L);
        when(routineExerciseRepository.save(any(RoutineExercise.class))).thenReturn(exercise);

        RoutineExercise createdExercise = routineExerciseUseCase.createRoutineExercise(exercise);

        assertNotNull(createdExercise);
        assertEquals(exercise.getId(), createdExercise.getId());
        verify(routineExerciseRepository, times(1)).save(exercise);
    }

    @Test
    void testGetRoutineExerciseByIdFound() {
        RoutineExercise exercise = new RoutineExercise(1L, 1L, 1L);
        when(routineExerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));

        RoutineExercise foundExercise = routineExerciseUseCase.getRoutineExerciseById(1L);

        assertNotNull(foundExercise);
        assertEquals(exercise.getId(), foundExercise.getId());
    }

    @Test
    void testGetRoutineExerciseByIdNotFound() {
        when(routineExerciseRepository.findById(1L)).thenReturn(Optional.empty());

        RoutineExercise foundExercise = routineExerciseUseCase.getRoutineExerciseById(1L);

        assertNull(foundExercise);
    }

    @Test
    void testGetAllRoutineExercises() {
        RoutineExercise exercise1 = new RoutineExercise(1L, 1L, 1L);
        RoutineExercise exercise2 = new RoutineExercise(2L, 1L, 2L);
        when(routineExerciseRepository.findAll()).thenReturn(Arrays.asList(exercise1, exercise2));

        List<RoutineExercise> exercises = routineExerciseUseCase.getAllRoutineExercises();

        assertEquals(2, exercises.size());
        assertEquals(exercise1.getId(), exercises.get(0).getId());
        assertEquals(exercise2.getId(), exercises.get(1).getId());
    }

    @Test
    void testUpdateRoutineExerciseFound() {
        RoutineExercise existingExercise = new RoutineExercise(1L, 1L, 1L);
        RoutineExercise updatedDetails = new RoutineExercise(null, 2L, 2L);

        when(routineExerciseRepository.findById(1L)).thenReturn(Optional.of(existingExercise));
        when(routineExerciseRepository.save(any(RoutineExercise.class))).thenReturn(existingExercise);

        RoutineExercise updatedExercise = routineExerciseUseCase.updateRoutineExercise(1L, updatedDetails);

        assertNotNull(updatedExercise);
        assertEquals(2L, updatedExercise.getRoutineId());
        assertEquals(2L, updatedExercise.getExerciseId());
        verify(routineExerciseRepository, times(1)).save(existingExercise);
    }

    @Test
    void testUpdateRoutineExerciseNotFound() {
        RoutineExercise updatedDetails = new RoutineExercise(null, 2L, 2L);
        when(routineExerciseRepository.findById(1L)).thenReturn(Optional.empty());

        RoutineExercise updatedExercise = routineExerciseUseCase.updateRoutineExercise(1L, updatedDetails);

        assertNull(updatedExercise);
    }

    @Test
    void testDeleteRoutineExerciseFound() {
        when(routineExerciseRepository.existsById(1L)).thenReturn(true);

        boolean result = routineExerciseUseCase.deleteRoutineExercise(1L);

        assertTrue(result);
        verify(routineExerciseRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteRoutineExerciseNotFound() {
        when(routineExerciseRepository.existsById(1L)).thenReturn(false);

        boolean result = routineExerciseUseCase.deleteRoutineExercise(1L);

        assertFalse(result);
        verify(routineExerciseRepository, never()).deleteById(anyLong());
    }
}

