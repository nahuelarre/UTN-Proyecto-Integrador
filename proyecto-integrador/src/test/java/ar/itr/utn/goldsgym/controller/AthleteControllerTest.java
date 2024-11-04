package ar.itr.utn.goldsgym.controller;

import ar.itr.utn.goldsgym.model.entities.Athlete;
import ar.itr.utn.goldsgym.usecase.AthleteUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class AthleteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AthleteUseCase athleteUseCase;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateAthlete() throws Exception {
        Athlete athlete = new Athlete(1L, "John Doe", 25, 70.0, 180, null, null, null, true);
        when(athleteUseCase.createAthlete(any(Athlete.class))).thenReturn(athlete);

        mockMvc.perform(post("/athletes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(athlete)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testGetAthlete() throws Exception {
        Athlete athlete = new Athlete(1L, "John Doe", 25, 70.0, 180, null, null, null, true);
        when(athleteUseCase.getAthlete(1L)).thenReturn(athlete);

        mockMvc.perform(get("/athletes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testGetAllAthletes() throws Exception {
        List<Athlete> athletes = Arrays.asList(
                new Athlete(1L, "John Doe", 25, 70.0, 180, null, null, null, true),
                new Athlete(2L, "Jane Doe", 30, 65.0, 170, null, null, null, true)
        );
        when(athleteUseCase.getAllAthletes()).thenReturn(athletes);

        mockMvc.perform(get("/athletes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testUpdateAthlete() throws Exception {
        Athlete updatedAthlete = new Athlete(1L, "John Doe Updated", 26, 72.0, 182, null, null, null, true);
        when(athleteUseCase.updateAthlete(anyLong(), any(Athlete.class))).thenReturn(updatedAthlete);

        mockMvc.perform(put("/athletes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAthlete)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe Updated"));
    }

    @Test
    public void testDeleteAthlete() throws Exception {
        when(athleteUseCase.deleteAthlete(1L)).thenReturn(true);

        mockMvc.perform(delete("/athletes/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteAthleteNotFound() throws Exception {
        when(athleteUseCase.deleteAthlete(1L)).thenReturn(false);

        mockMvc.perform(delete("/athletes/{id}", 1L))
                .andExpect(status().isNotFound());
    }
}

