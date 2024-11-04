package ar.itr.utn.goldsgym.usecase;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import ar.itr.utn.goldsgym.model.dto.AthleteDTO;
import ar.itr.utn.goldsgym.model.entities.Athlete;
import ar.itr.utn.goldsgym.model.repositories.AthleteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class MasterUseCaseTest {

    @Mock
    private AthleteRepository athleteRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private MasterUseCase masterUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAthletes() {
        Athlete athlete1 = new Athlete(1L, "John Doe", 25, 70.0, 175, null, null, null, true);
        Athlete athlete2 = new Athlete(2L, "Jane Smith", 30, 65.0, 160, null, null, null, true);

        when(athleteRepository.findAll()).thenReturn(Arrays.asList(athlete1, athlete2));

        List<AthleteDTO> athletes = masterUseCase.getAllAthletes();

        assertEquals(2, athletes.size());
        assertEquals("John Doe", athletes.get(0).getName());
        assertEquals("Jane Smith", athletes.get(1).getName());
    }

    @Test
    void testGetAthleteById() {
        Athlete athlete = new Athlete(1L, "John Doe", 25, 70.0, 175, null, null, null, true);

        when(athleteRepository.findById(1L)).thenReturn(Optional.of(athlete));

        AthleteDTO athleteDTO = masterUseCase.getAthleteById(1L);

        assertNotNull(athleteDTO);
        assertEquals("John Doe", athleteDTO.getName());
    }

    @Test
    void testGetAthleteById_NotFound() {
        when(athleteRepository.findById(1L)).thenReturn(Optional.empty());

        AthleteDTO athleteDTO = masterUseCase.getAthleteById(1L);

        assertNull(athleteDTO);
    }
}

