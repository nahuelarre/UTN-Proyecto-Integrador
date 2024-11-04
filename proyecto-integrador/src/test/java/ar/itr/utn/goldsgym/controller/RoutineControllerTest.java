package ar.itr.utn.goldsgym.controller;

import ar.itr.utn.goldsgym.model.entities.Routine;
import ar.itr.utn.goldsgym.usecase.RoutineUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
public class RoutineControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private RoutineController routineController;

    @Mock
    private RoutineUseCase routineUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(routineController).build();
    }

    @Test
    public void testCreateRoutine() throws Exception {
        Routine routine = new Routine(1L, "Morning Workout", "A quick morning routine", 30, 4, true);
        when(routineUseCase.createRoutine(any(Routine.class))).thenReturn(routine);

        mockMvc.perform(post("/routines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Morning Workout\", \"description\": \"A quick morning routine\", \"duration\": 30, \"weeklyFrequency\": 4, \"active\": true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Morning Workout"))
                .andExpect(jsonPath("$.description").value("A quick morning routine"))
                .andExpect(jsonPath("$.duration").value(30))
                .andExpect(jsonPath("$.weeklyFrequency").value(4))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    public void testGetRoutine() throws Exception {
        Routine routine = new Routine(1L, "Morning Workout", "A quick morning routine", 30, 4, true);
        when(routineUseCase.getRoutine(1L)).thenReturn(routine);

        mockMvc.perform(get("/routines/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Morning Workout"))
                .andExpect(jsonPath("$.description").value("A quick morning routine"))
                .andExpect(jsonPath("$.duration").value(30))
                .andExpect(jsonPath("$.weeklyFrequency").value(4))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    public void testGetRoutine_NotFound() throws Exception {
        when(routineUseCase.getRoutine(1L)).thenReturn(null);

        mockMvc.perform(get("/routines/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllRoutines() throws Exception {
        Routine routine1 = new Routine(1L, "Morning Workout", "A quick morning routine", 30, 4, true);
        Routine routine2 = new Routine(2L, "Evening Workout", "A relaxing evening routine", 45, 3, false);
        List<Routine> routines = Arrays.asList(routine1, routine2);
        when(routineUseCase.getAllRoutines()).thenReturn(routines);

        mockMvc.perform(get("/routines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(2)));
    }

    @Test
    public void testUpdateRoutine() throws Exception {
        Routine existingRoutine = new Routine(1L, "Morning Workout", "A quick morning routine", 30, 4, true);
        Routine updatedRoutine = new Routine(1L, "Updated Workout", "An updated description", 40, 5, true);
        when(routineUseCase.updateRoutine(anyLong(), any(Routine.class))).thenReturn(updatedRoutine);

        mockMvc.perform(put("/routines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Workout\", \"description\": \"An updated description\", \"duration\": 40, \"weeklyFrequency\": 5, \"active\": true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Workout"))
                .andExpect(jsonPath("$.description").value("An updated description"))
                .andExpect(jsonPath("$.duration").value(40))
                .andExpect(jsonPath("$.weeklyFrequency").value(5))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    public void testUpdateRoutine_NotFound() throws Exception {
        when(routineUseCase.updateRoutine(anyLong(), any(Routine.class))).thenReturn(null);

        mockMvc.perform(put("/routines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Workout\", \"description\": \"An updated description\", \"duration\": 40, \"weeklyFrequency\": 5, \"active\": true}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteRoutine() throws Exception {
        when(routineUseCase.deleteRoutine(1L)).thenReturn(true);

        mockMvc.perform(delete("/routines/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteRoutine_NotFound() throws Exception {
        when(routineUseCase.deleteRoutine(1L)).thenReturn(false);

        mockMvc.perform(delete("/routines/1"))
                .andExpect(status().isNotFound());
    }
}

