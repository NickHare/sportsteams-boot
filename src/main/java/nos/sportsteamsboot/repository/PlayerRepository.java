package nos.sportsteamsboot.repository;

import nos.sportsteamsboot.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findById(Long id);
    Optional<Player> findByName(String name);
    Optional<Player> findByExternalId(String extId);
    List<Player> findAll();
}
