package ar.itr.utn.goldsgym.usecase;

import ar.itr.utn.goldsgym.model.entities.Routine;
import ar.itr.utn.goldsgym.model.repositories.RoutineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RoutineUseCaseTest {

    @InjectMocks
    private RoutineUseCase routineUseCase;

    @Mock
    private RoutineRepository routineRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateRoutine() {
        Routine routine = new Routine(null, "Morning Workout", "A quick morning routine", 30, 4, true);
        Routine savedRoutine = new Routine(1L, "Morning Workout", "A quick morning routine", 30, 4, true);

        when(routineRepository.save(any(Routine.class))).thenReturn(savedRoutine);

        Routine createdRoutine = routineUseCase.createRoutine(routine);

        assertNotNull(createdRoutine);
        assertEquals(1L, createdRoutine.getId());
        assertEquals("Morning Workout", createdRoutine.getName());
        assertEquals("A quick morning routine", createdRoutine.getDescription());
        assertEquals(30, createdRoutine.getDuration());
        assertEquals(4, createdRoutine.getWeeklyFrequency());
        assertTrue(createdRoutine.isActive());
    }

    @Test
    public void testGetRoutine() {
        Routine routine = new Routine(1L, "Morning Workout", "A quick morning routine", 30, 4, true);
        when(routineRepository.findById(1L)).thenReturn(Optional.of(routine));

        Routine retrievedRoutine = routineUseCase.getRoutine(1L);

        assertNotNull(retrievedRoutine);
        assertEquals(1L, retrievedRoutine.getId());
        assertEquals("Morning Workout", retrievedRoutine.getName());
    }

    @Test
    public void testGetRoutine_NotFound() {
        when(routineRepository.findById(1L)).thenReturn(Optional.empty());

        Routine retrievedRoutine = routineUseCase.getRoutine(1L);

        assertNull(retrievedRoutine);
    }

    @Test
    public void testGetAllRoutines() {
        Routine routine1 = new Routine(1L, "Morning Workout", "A quick morning routine", 30, 4, true);
        Routine routine2 = new Routine(2L, "Evening Workout", "A relaxing evening routine", 45, 3, false);
        List<Routine> routines = Arrays.asList(routine1, routine2);

        when(routineRepository.findAll()).thenReturn(routines);

        List<Routine> allRoutines = routineUseCase.getAllRoutines();

        assertEquals(2, allRoutines.size());
        assertEquals("Morning Workout", allRoutines.get(0).getName());
        assertEquals("Evening Workout", allRoutines.get(1).getName());
    }

    @Test
    public void testUpdateRoutine() {
        Routine existingRoutine = new Routine(1L, "Morning Workout", "A quick morning routine", 30, 4, true);
        Routine updatedDetails = new Routine(null, "Updated Workout", "Updated description", 40, 5, false);

        when(routineRepository.findById(1L)).thenReturn(Optional.of(existingRoutine));
        when(routineRepository.save(any(Routine.class))).thenReturn(new Routine(1L, "Updated Workout", "Updated description", 40, 5, false));

        Routine updatedRoutine = routineUseCase.updateRoutine(1L, updatedDetails);

        assertNotNull(updatedRoutine);
        assertEquals("Updated Workout", updatedRoutine.getName());
        assertEquals("Updated description", updatedRoutine.getDescription());
        assertEquals(40, updatedRoutine.getDuration());
        assertEquals(5, updatedRoutine.getWeeklyFrequency());
        assertFalse(updatedRoutine.isActive());
    }

    @Test
    public void testUpdateRoutine_NotFound() {
        Routine updatedDetails = new Routine(null, "Updated Workout", "Updated description", 40, 5, false);
        when(routineRepository.findById(anyLong())).thenReturn(Optional.empty());

        Routine updatedRoutine = routineUseCase.updateRoutine(1L, updatedDetails);

        assertNull(updatedRoutine);
    }

    @Test
    public void testDeleteRoutine() {
        when(routineRepository.existsById(1L)).thenReturn(true);
        doNothing().when(routineRepository).deleteById(1L);

        boolean isDeleted = routineUseCase.deleteRoutine(1L);

        assertTrue(isDeleted);
        verify(routineRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteRoutine_NotFound() {
        when(routineRepository.existsById(1L)).thenReturn(false);

        boolean isDeleted = routineUseCase.deleteRoutine(1L);

        assertFalse(isDeleted);
        verify(routineRepository, never()).deleteById(1L);
    }
}

