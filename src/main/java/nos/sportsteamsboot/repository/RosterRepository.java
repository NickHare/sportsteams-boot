package nos.sportsteamsboot.repository;

import nos.sportsteamsboot.model.Roster;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RosterRepository extends JpaRepository<Roster, Long>, JpaSpecificationExecutor<Roster> {
}
