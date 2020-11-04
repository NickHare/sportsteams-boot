package nos.sportsteamsboot.repository;

import nos.sportsteamsboot.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByName(String name);
}
