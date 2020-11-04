package nos.sportsteamsboot.repository;

import nos.sportsteamsboot.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByName(String name);
    List<Player> findAll();
}
