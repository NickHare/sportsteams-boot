package nos.sportsteamsboot.repository;

import nos.sportsteamsboot.model.Game;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Long>, JpaSpecificationExecutor<Game> {
}
