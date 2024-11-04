package ar.itr.utn.goldsgym.usecase;

import ar.itr.utn.goldsgym.model.dto.AthleteDTO;
import ar.itr.utn.goldsgym.model.dto.ExerciseDTO;
import ar.itr.utn.goldsgym.model.dto.RoutineDTO;
import ar.itr.utn.goldsgym.model.entities.Athlete;
import ar.itr.utn.goldsgym.model.entities.Routine;
import ar.itr.utn.goldsgym.model.entities.Exercise;
import ar.itr.utn.goldsgym.model.repositories.AthleteRepository;
import ar.itr.utn.goldsgym.model.repositories.RoutineRepository;
import ar.itr.utn.goldsgym.model.repositories.ExerciseRoutineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MasterUseCase {

    private final AthleteRepository athleteRepository;
    private final RoutineRepository routineRepository;
    private final ExerciseRoutineRepository exerciseRoutineRepository;

    public MasterUseCase(AthleteRepository athleteRepository, RoutineRepository routineRepository, ExerciseRoutineRepository exerciseRoutineRepository) {
        this.athleteRepository = athleteRepository;
        this.routineRepository = routineRepository;
        this.exerciseRoutineRepository = exerciseRoutineRepository;
    }

    public List<AthleteDTO> getAllAthletes() {
        return StreamSupport.stream(athleteRepository.findAll().spliterator(), false)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AthleteDTO getAthleteById(Long id) {
        Optional<Athlete> athleteOptional = athleteRepository.findById(id);
        return athleteOptional.map(this::mapToDTO).orElse(null);
    }

    private AthleteDTO mapToDTO(Athlete athlete) {
        AthleteDTO athleteDTO = new AthleteDTO();
        athleteDTO.setId(athlete.getId());
        athleteDTO.setName(athlete.getName());
        athleteDTO.setAge(athlete.getAge());
        athleteDTO.setWeight(athlete.getWeight());
        athleteDTO.setHeight(athlete.getHeight());
        athleteDTO.setStartingDate(athlete.getStartingDate());
        athleteDTO.setGoal(athlete.getGoal());
        athleteDTO.setLevel(athlete.getLevel());
        athleteDTO.setActive(athlete.isActive());

        List<RoutineDTO> routines = routineRepository.findById(athlete.getId())
                .stream()
                .map(this::mapToRoutineDTO)
                .collect(Collectors.toList());

        athleteDTO.setRoutines(routines);
        return athleteDTO;
    }

    private RoutineDTO mapToRoutineDTO(Routine routine) {
        RoutineDTO routineDTO = new RoutineDTO();
        routineDTO.setId(routine.getId());
        routineDTO.setName(routine.getName());
        routineDTO.setDescription(routine.getDescription());
        routineDTO.setDuration(routine.getDuration());
        routineDTO.setWeeklyFrequency(routine.getWeeklyFrequency());
        routineDTO.setActive(routine.isActive());

        List<ExerciseDTO> exercises = exerciseRoutineRepository.findById(routine.getId())
                .stream()
                .map(this::mapToExerciseDTO)
                .collect(Collectors.toList());

        routineDTO.setExercices(exercises);
        return routineDTO;
    }

    private ExerciseDTO mapToExerciseDTO(Exercise exercise) {
        ExerciseDTO exerciseDTO = new ExerciseDTO();
        exerciseDTO.setId(exercise.getId());
        exerciseDTO.setExerciseList(exercise.getExerciseList());
        exerciseDTO.setMuscleGroup(exercise.getMuscleGroup());
        exerciseDTO.setSets(exercise.getSets());
        exerciseDTO.setRepetitions(exercise.getRepetitions());
        exerciseDTO.setDayOfWeek(exercise.getDayOfWeek());
        return exerciseDTO;
    }
}


