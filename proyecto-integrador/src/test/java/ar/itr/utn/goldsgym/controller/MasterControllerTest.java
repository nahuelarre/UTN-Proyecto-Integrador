package ar.itr.utn.goldsgym.controller;

import ar.itr.utn.goldsgym.model.dto.AthleteDTO;
import ar.itr.utn.goldsgym.usecase.MasterUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MasterControllerTest {

    private MasterUseCase masterUseCase;
    private MasterController masterController;

    @BeforeEach
    void setUp() {
        masterUseCase = mock(MasterUseCase.class);
        masterController = new MasterController(masterUseCase);
    }

    @Test
    void testGetAllAthletes() {
        AthleteDTO athlete1 = new AthleteDTO(/* initial parameters */);
        AthleteDTO athlete2 = new AthleteDTO(/* initial parameters */);
        List<AthleteDTO> athletes = Arrays.asList(athlete1, athlete2);

        when(masterUseCase.getAllAthletes()).thenReturn(athletes);

        List<AthleteDTO> result = masterController.getAllAthletes();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(athlete1, result.get(0));
        assertEquals(athlete2, result.get(1));
        verify(masterUseCase, times(1)).getAllAthletes();
    }

    @Test
    void testGetAthleteByIdFound() {
        Long athleteId = 1L;
        AthleteDTO athleteDTO = new AthleteDTO(/* initial parameters */);
        when(masterUseCase.getAthleteById(athleteId)).thenReturn(athleteDTO);

        ResponseEntity<AthleteDTO> response = masterController.getAthleteById(athleteId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(athleteDTO, response.getBody());
        verify(masterUseCase, times(1)).getAthleteById(athleteId);
    }

    @Test
    void testGetAthleteByIdNotFound() {
        Long athleteId = 1L;
        when(masterUseCase.getAthleteById(athleteId)).thenReturn(null);

        ResponseEntity<AthleteDTO> response = masterController.getAthleteById(athleteId);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(masterUseCase, times(1)).getAthleteById(athleteId);
    }
}
