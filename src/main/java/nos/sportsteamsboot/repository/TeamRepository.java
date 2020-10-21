package nos.sportsteamsboot.repository;

import nos.sportsteamsboot.model.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Long> {
    Team findByName(String name);
}
