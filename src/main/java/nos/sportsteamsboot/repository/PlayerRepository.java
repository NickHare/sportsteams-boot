package nos.sportsteamsboot.repository;

import nos.sportsteamsboot.model.Player;
import nos.sportsteamsboot.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByName(String name);
    Optional<Team> findByExternalId(String extId);
}
