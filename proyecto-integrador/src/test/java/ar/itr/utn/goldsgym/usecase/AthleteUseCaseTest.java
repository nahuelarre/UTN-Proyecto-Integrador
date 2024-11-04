package ar.itr.utn.goldsgym.usecase;

import ar.itr.utn.goldsgym.model.entities.Athlete;
import ar.itr.utn.goldsgym.model.repositories.AthleteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AthleteUseCaseTest {

    @Autowired
    private AthleteUseCase athleteUseCase;

    @MockBean
    private AthleteRepository athleteRepository;

    private Athlete athlete;

    @BeforeEach
    public void setUp() {
        athlete = new Athlete(1L, "John Doe", 25, 70.0, 180, null, null, null, true);
    }

    @Test
    public void testCreateAthlete() {
        when(athleteRepository.save(any(Athlete.class))).thenReturn(athlete);

        Athlete createdAthlete = athleteUseCase.createAthlete(new Athlete());
        assertNotNull(createdAthlete);
        assertEquals("John Doe", createdAthlete.getName());
    }

    @Test
    public void testGetAthlete() {
        when(athleteRepository.findById(anyLong())).thenReturn(Optional.of(athlete));

        Athlete foundAthlete = athleteUseCase.getAthlete(1L);
        assertNotNull(foundAthlete);
        assertEquals("John Doe", foundAthlete.getName());
    }

    @Test
    public void testGetAllAthletes() {
        when(athleteRepository.findAll()).thenReturn(Arrays.asList(athlete));

        List<Athlete> athletes = athleteUseCase.getAllAthletes();
        assertEquals(1, athletes.size());
        assertEquals("John Doe", athletes.get(0).getName());
    }

    @Test
    public void testUpdateAthlete() {
        when(athleteRepository.findById(anyLong())).thenReturn(Optional.of(athlete));
        when(athleteRepository.save(any(Athlete.class))).thenReturn(athlete);

        Athlete updatedAthlete = athleteUseCase.updateAthlete(1L, athlete);
        assertNotNull(updatedAthlete);
        assertEquals("John Doe", updatedAthlete.getName());
    }

    @Test
    public void testDeleteAthlete() {
        when(athleteRepository.existsById(anyLong())).thenReturn(true);

        boolean isDeleted = athleteUseCase.deleteAthlete(1L);
        assertTrue(isDeleted);
        Mockito.verify(athleteRepository).deleteById(1L);
    }

    @Test
    public void testDeleteAthleteNotFound() {
        when(athleteRepository.existsById(anyLong())).thenReturn(false);

        boolean isDeleted = athleteUseCase.deleteAthlete(1L);
        assertFalse(isDeleted);
    }
}

