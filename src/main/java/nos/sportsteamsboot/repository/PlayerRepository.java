package nos.sportsteamsboot.repository;

import nos.sportsteamsboot.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {
    Player findByName(String name);
}
