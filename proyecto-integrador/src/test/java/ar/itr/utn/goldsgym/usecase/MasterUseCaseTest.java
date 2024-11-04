package ar.itr.utn.goldsgym.usecase;

import ar.itr.utn.goldsgym.model.dto.AthleteDTO;
import ar.itr.utn.goldsgym.model.entities.Athlete;
import ar.itr.utn.goldsgym.model.repositories.AthleteRepository;
import ar.itr.utn.goldsgym.model.repositories.RoutineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
public class MasterUseCaseTest {

    @Mock
    private AthleteRepository athleteRepository;

    @Mock
    private RoutineRepository routineRepository;

    @InjectMocks
    private MasterUseCase masterUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAthletes() {
        // Preparar datos de prueba
        Athlete athlete1 = new Athlete(1L, "John Doe", 25, 70.0, 175, LocalDate.of(2023, 1, 1), null, null, true);
        Athlete athlete2 = new Athlete(2L, "Jane Doe", 30, 65.0, 160, LocalDate.of(2022, 1, 1), null, null, true);

        when(athleteRepository.findAll()).thenReturn(Arrays.asList(athlete1, athlete2));

        // Ejecutar método
        List<AthleteDTO> athleteDTOs = masterUseCase.getAllAthletes();

        // Verificar resultados
        assertNotNull(athleteDTOs);
        assertEquals(2, athleteDTOs.size());
        assertEquals("John Doe", athleteDTOs.get(0).getName());
        assertEquals("Jane Doe", athleteDTOs.get(1).getName());
    }

    @Test
    void testGetAthleteById() {
        // Preparar datos de prueba
        Athlete athlete = new Athlete(1L, "John Doe", 25, 70.0, 175, LocalDate.of(2023, 1, 1), null, null, true);

        when(athleteRepository.findById(1L)).thenReturn(Optional.of(athlete));

        // Ejecutar método
        AthleteDTO athleteDTO = masterUseCase.getAthleteById(1L);

        // Verificar resultados
        assertNotNull(athleteDTO);
        assertEquals("John Doe", athleteDTO.getName());
    }
}
