package nos.sportsteamsboot.repository;

import nos.sportsteamsboot.model.Roster;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface RosterRepository extends CrudRepository<Roster, Long>, JpaSpecificationExecutor<Roster> {
}
