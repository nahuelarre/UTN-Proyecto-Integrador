package ar.itr.utn.goldsgym.usecase;

import ar.itr.utn.goldsgym.model.dto.AthleteDTO;
import ar.itr.utn.goldsgym.model.dto.ExerciseDTO;
import ar.itr.utn.goldsgym.model.dto.RoutineDTO;
import ar.itr.utn.goldsgym.model.entities.Athlete;
import ar.itr.utn.goldsgym.model.entities.ExerciseList;
import ar.itr.utn.goldsgym.model.entities.MuscleGroup;
import ar.itr.utn.goldsgym.model.repositories.AthleteRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MasterUseCase {

    private final AthleteRepository athleteRepository;
    private final JdbcTemplate jdbcTemplate;

    public MasterUseCase(AthleteRepository athleteRepository, JdbcTemplate jdbcTemplate) {
        this.athleteRepository = athleteRepository;
        this.jdbcTemplate = jdbcTemplate;
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

        List<RoutineDTO> routines = getRoutinesByAthleteId(athlete.getId());
        athleteDTO.setRoutines(routines);

        return athleteDTO;
    }

    private List<RoutineDTO> getRoutinesByAthleteId(Long athleteId) {
        String sql = "SELECT r.* FROM routine r " +
                "JOIN athlete_routine ar ON r.id = ar.routine_id " +
                "WHERE ar.athlete_id = ?";
        return jdbcTemplate.query(sql, new Object[]{athleteId}, (rs, rowNum) -> {
            RoutineDTO routineDTO = new RoutineDTO();
            routineDTO.setId(rs.getLong("id"));
            routineDTO.setName(rs.getString("name"));
            routineDTO.setDescription(rs.getString("description"));
            routineDTO.setDuration(rs.getInt("duration"));
            routineDTO.setWeeklyFrequency(rs.getInt("weekly_frequency"));
            routineDTO.setActive(rs.getBoolean("active"));
            routineDTO.setExercices(getExercisesByRoutineId(routineDTO.getId())); // Llama para obtener ejercicios
            return routineDTO;
        });
    }

    private List<ExerciseDTO> getExercisesByRoutineId(Long routineId) {
        String sql = "SELECT e.* FROM exercise e " +
                "JOIN routine_exercise re ON e.id = re.exercise_id " +
                "WHERE re.routine_id = ?";
        return jdbcTemplate.query(sql, new Object[]{routineId}, (rs, rowNum) -> {
            ExerciseDTO exerciseDTO = new ExerciseDTO();
            exerciseDTO.setId(rs.getLong("id"));
            exerciseDTO.setExerciseList(ExerciseList.valueOf(rs.getString("exercise_list")));
            exerciseDTO.setMuscleGroup(MuscleGroup.valueOf(rs.getString("muscle_group")));
            exerciseDTO.setSets(rs.getInt("sets"));
            exerciseDTO.setRepetitions(rs.getInt("repetitions"));
            exerciseDTO.setDayOfWeek(DayOfWeek.valueOf(rs.getString("day_of_week")));
            return exerciseDTO;
        });
    }
}


