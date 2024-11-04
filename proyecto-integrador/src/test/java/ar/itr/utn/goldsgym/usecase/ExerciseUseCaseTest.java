package ar.itr.utn.goldsgym.usecase;

import ar.itr.utn.goldsgym.model.entities.Exercise;
import ar.itr.utn.goldsgym.model.entities.ExerciseList;
import ar.itr.utn.goldsgym.model.entities.MuscleGroup;
import ar.itr.utn.goldsgym.model.repositories.ExerciseRoutineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ExerciseUseCaseTest {

    @InjectMocks
    private ExerciseUseCase exerciseUseCase;

    @Mock
    private ExerciseRoutineRepository exerciseRoutineRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateExerciseRoutine() {
        Exercise exercise = new Exercise(1L, ExerciseList.PUSH_UP, MuscleGroup.CHEST, 3, 10, DayOfWeek.MONDAY);
        when(exerciseRoutineRepository.save(any(Exercise.class))).thenReturn(exercise);

        Exercise createdExercise = exerciseUseCase.createExerciseRoutine(exercise);

        assertNotNull(createdExercise);
        assertEquals(1L, createdExercise.getId());
        assertEquals(3, createdExercise.getSets());
        assertEquals(10, createdExercise.getRepetitions());
    }

    @Test
    public void testGetExerciseRoutine() {
        Exercise exercise = new Exercise(1L, ExerciseList.PUSH_UP, MuscleGroup.CHEST, 3, 10, DayOfWeek.MONDAY);
        when(exerciseRoutineRepository.findById(1L)).thenReturn(Optional.of(exercise));

        Exercise foundExercise = exerciseUseCase.getExerciseRoutine(1L);

        assertNotNull(foundExercise);
        assertEquals(1L, foundExercise.getId());
    }

    @Test
    public void testGetAllExerciseRoutines() {
        Exercise exercise = new Exercise(1L, ExerciseList.PUSH_UP, MuscleGroup.CHEST, 3, 10, DayOfWeek.MONDAY);
        when(exerciseRoutineRepository.findAll()).thenReturn(Collections.singletonList(exercise));

        List<Exercise> exercises = exerciseUseCase.getAllExerciseRoutines();

        assertEquals(1, exercises.size());
        assertEquals(1L, exercises.get(0).getId());
    }

    @Test
    public void testUpdateExerciseRoutine() {
        Exercise existingExercise = new Exercise(1L, ExerciseList.PUSH_UP, MuscleGroup.CHEST, 3, 10, DayOfWeek.MONDAY);
        Exercise updatedExercise = new Exercise(1L, ExerciseList.DUMBBELL_FLY, MuscleGroup.CHEST, 4, 12, DayOfWeek.TUESDAY);

        when(exerciseRoutineRepository.findById(1L)).thenReturn(Optional.of(existingExercise));
        when(exerciseRoutineRepository.save(any(Exercise.class))).thenReturn(updatedExercise);

        Exercise result = exerciseUseCase.updateExerciseRoutine(1L, updatedExercise);

        assertNotNull(result);
        assertEquals(4, result.getSets());
        assertEquals(12, result.getRepetitions());
    }

    @Test
    public void testDeleteExerciseRoutine() {
        when(exerciseRoutineRepository.existsById(1L)).thenReturn(true);

        boolean result = exerciseUseCase.deleteExerciseRoutine(1L);

        assertTrue(result);
        verify(exerciseRoutineRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteExerciseRoutine_NotFound() {
        when(exerciseRoutineRepository.existsById(1L)).thenReturn(false);

        boolean result = exerciseUseCase.deleteExerciseRoutine(1L);

        assertFalse(result);
        verify(exerciseRoutineRepository, never()).deleteById(any(Long.class));
    }
}

