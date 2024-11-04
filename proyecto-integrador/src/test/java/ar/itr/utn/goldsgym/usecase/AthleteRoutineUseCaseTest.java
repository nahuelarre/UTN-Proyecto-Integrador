package ar.itr.utn.goldsgym.usecase;

import ar.itr.utn.goldsgym.model.entities.AthleteRoutine;
import ar.itr.utn.goldsgym.model.repositories.AthleteRoutineRepository;
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

public class AthleteRoutineUseCaseTest {

    @InjectMocks
    private AthleteRoutineUseCase athleteRoutineUseCase;

    @Mock
    private AthleteRoutineRepository athleteRoutineRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAthleteRoutine() {
        AthleteRoutine athleteRoutine = new AthleteRoutine(null, 1L, 2L);
        AthleteRoutine savedAthleteRoutine = new AthleteRoutine(1L, 1L, 2L);

        when(athleteRoutineRepository.save(any(AthleteRoutine.class))).thenReturn(savedAthleteRoutine);

        AthleteRoutine result = athleteRoutineUseCase.createAthleteRoutine(athleteRoutine);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getAthleteId());
        assertEquals(2L, result.getRoutineId());
    }

    @Test
    public void testGetAthleteRoutineById() {
        AthleteRoutine athleteRoutine = new AthleteRoutine(1L, 1L, 2L);

        when(athleteRoutineRepository.findById(1L)).thenReturn(Optional.of(athleteRoutine));

        AthleteRoutine result = athleteRoutineUseCase.getAthleteRoutineById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testGetAthleteRoutineById_NotFound() {
        when(athleteRoutineRepository.findById(anyLong())).thenReturn(Optional.empty());

        AthleteRoutine result = athleteRoutineUseCase.getAthleteRoutineById(1L);

        assertNull(result);
    }

    @Test
    public void testGetAllAthleteRoutines() {
        AthleteRoutine athleteRoutine1 = new AthleteRoutine(1L, 1L, 2L);
        AthleteRoutine athleteRoutine2 = new AthleteRoutine(2L, 2L, 3L);
        when(athleteRoutineRepository.findAll()).thenReturn(Arrays.asList(athleteRoutine1, athleteRoutine2));

        List<AthleteRoutine> result = athleteRoutineUseCase.getAllAthleteRoutines();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testUpdateAthleteRoutine() {
        AthleteRoutine existingRoutine = new AthleteRoutine(1L, 1L, 2L);
        AthleteRoutine updatedDetails = new AthleteRoutine(null, 1L, 3L);

        when(athleteRoutineRepository.findById(1L)).thenReturn(Optional.of(existingRoutine));
        when(athleteRoutineRepository.save(any(AthleteRoutine.class))).thenReturn(new AthleteRoutine(1L, 1L, 3L));

        AthleteRoutine result = athleteRoutineUseCase.updateAthleteRoutine(1L, updatedDetails);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(3L, result.getRoutineId());
    }

    @Test
    public void testUpdateAthleteRoutine_NotFound() {
        AthleteRoutine updatedDetails = new AthleteRoutine(null, 1L, 3L);
        when(athleteRoutineRepository.findById(anyLong())).thenReturn(Optional.empty());

        AthleteRoutine result = athleteRoutineUseCase.updateAthleteRoutine(1L, updatedDetails);

        assertNull(result);
    }

    @Test
    public void testDeleteAthleteRoutine() {
        when(athleteRoutineRepository.existsById(1L)).thenReturn(true);

        boolean result = athleteRoutineUseCase.deleteAthleteRoutine(1L);

        assertTrue(result);
        verify(athleteRoutineRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteAthleteRoutine_NotFound() {
        when(athleteRoutineRepository.existsById(1L)).thenReturn(false);

        boolean result = athleteRoutineUseCase.deleteAthleteRoutine(1L);

        assertFalse(result);
        verify(athleteRoutineRepository, never()).deleteById(anyLong());
    }
}

