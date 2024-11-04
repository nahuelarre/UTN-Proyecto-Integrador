package ar.itr.utn.goldsgym.usecase;

import ar.itr.utn.goldsgym.model.entities.Athlete;
import ar.itr.utn.goldsgym.model.repositories.AthleteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AthleteUseCase {

    private final AthleteRepository athleteRepository;

    public AthleteUseCase(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    public Athlete createAthlete(Athlete athlete) {
        athlete.setId(null);
        return athleteRepository.save(athlete);
    }

    public Athlete getAthlete(Long id) {
        return athleteRepository.findById(id).orElse(null);
    }

    public List<Athlete> getAllAthletes() {
        return (List<Athlete>) athleteRepository.findAll();
    }

    public Athlete updateAthlete(Long id, Athlete athleteDetails) {
        Optional<Athlete> optionalAthlete = athleteRepository.findById(id);

        if (optionalAthlete.isPresent()) {
            Athlete athlete = optionalAthlete.get();
            athlete.setName(athleteDetails.getName());
            athlete.setAge(athleteDetails.getAge());
            athlete.setWeight(athleteDetails.getWeight());
            athlete.setHeight(athleteDetails.getHeight());
            athlete.setStartingDate(athleteDetails.getStartingDate());
            athlete.setGoal(athleteDetails.getGoal());
            athlete.setLevel(athleteDetails.getLevel());
            athlete.setActive(athleteDetails.isActive());

            return athleteRepository.save(athlete);
        } else {
            return null;
        }
    }

    public boolean deleteAthlete(Long id) {
        if (athleteRepository.existsById(id)) {
            athleteRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

