package ar.itr.utn.goldsgym.controller;

import ar.itr.utn.goldsgym.model.entities.Exercise;
import ar.itr.utn.goldsgym.model.entities.ExerciseList;
import ar.itr.utn.goldsgym.model.entities.MuscleGroup;
import ar.itr.utn.goldsgym.usecase.ExerciseUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class ExerciseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExerciseUseCase exerciseUseCase;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateExerciseRoutine() throws Exception {
        // Crea un ejercicio con valores válidos utilizando los enums
        Exercise exercise = new Exercise(1L, ExerciseList.PUSH_UP, MuscleGroup.CHEST, 3, 10, DayOfWeek.MONDAY);
        when(exerciseUseCase.createExerciseRoutine(any(Exercise.class))).thenReturn(exercise);

        mockMvc.perform(post("/exercise-routines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exercise)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.sets").value(3))
                .andExpect(jsonPath("$.repetitions").value(10))
                .andExpect(jsonPath("$.dayOfWeek").value("MONDAY"));
    }

    @Test
    public void testGetExerciseRoutine() throws Exception {
        Exercise exercise = new Exercise(1L, ExerciseList.PUSH_UP, MuscleGroup.CHEST, 3, 10, DayOfWeek.MONDAY);

        when(exerciseUseCase.getExerciseRoutine(1L)).thenReturn(exercise);

        mockMvc.perform(get("/exercise-routines/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.sets").value(3));
    }

    @Test
    public void testGetAllExerciseRoutines() throws Exception {
        Exercise exercise = new Exercise(1L, ExerciseList.PUSH_UP, MuscleGroup.CHEST, 3, 10, DayOfWeek.MONDAY);
        when(exerciseUseCase.getAllExerciseRoutines()).thenReturn(Collections.singletonList(exercise));

        mockMvc.perform(get("/exercise-routines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    public void testUpdateExerciseRoutine() throws Exception {
        Exercise existingExercise = new Exercise(1L, ExerciseList.PUSH_UP, MuscleGroup.CHEST, 3, 10, DayOfWeek.MONDAY);
        Exercise updatedExercise = new Exercise(1L, ExerciseList.DUMBBELL_FLY, MuscleGroup.CHEST, 4, 12, DayOfWeek.TUESDAY);

        when(exerciseUseCase.updateExerciseRoutine(1L, updatedExercise)).thenReturn(updatedExercise);

        mockMvc.perform(put("/exercise-routines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedExercise)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sets").value(4))
                .andExpect(jsonPath("$.repetitions").value(12));
    }

    @Test
    public void testDeleteExerciseRoutine() throws Exception {
        when(exerciseUseCase.deleteExerciseRoutine(1L)).thenReturn(true);

        mockMvc.perform(delete("/exercise-routines/1"))
                .andExpect(status().isNoContent());
    }
}

