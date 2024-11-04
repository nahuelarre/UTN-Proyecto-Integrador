package ar.itr.utn.goldsgym.usecase;

import ar.itr.utn.goldsgym.model.entities.Routine;
import ar.itr.utn.goldsgym.model.repositories.RoutineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RoutineUseCase {
    private final RoutineRepository routineRepository;

    public RoutineUseCase(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
    }

    public Routine createRoutine(Routine routine) {
        return routineRepository.save(routine);
    }

    public Routine getRoutine(Long id) {
        return routineRepository.findById(id).orElse(null);
    }

    public List<Routine> getAllRoutines() {
        return (List<Routine>) routineRepository.findAll();
    }

    public Routine updateRoutine(Long id, Routine routineDetails) {
        Optional<Routine> optionalRoutine = routineRepository.findById(id);

        if (optionalRoutine.isPresent()) {
            Routine routine = optionalRoutine.get();
            routine.setName(routineDetails.getName());
            routine.setDescription(routineDetails.getDescription());
            routine.setDuration(routineDetails.getDuration());
            routine.setWeeklyFrequency(routineDetails.getWeeklyFrequency());
            routine.setActive(routineDetails.isActive());

            return routineRepository.save(routine);
        } else {
            return null;
        }
    }

    public boolean deleteRoutine(Long id) {
        if (routineRepository.existsById(id)) {
            routineRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
