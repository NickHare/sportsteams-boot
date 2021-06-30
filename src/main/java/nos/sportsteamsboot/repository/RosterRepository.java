package nos.sportsteamsboot.repository;

import nos.sportsteamsboot.model.Roster;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RosterRepository extends JpaRepository<Roster, Long>, JpaSpecificationExecutor<Roster> {
    @EntityGraph(value="roster", type= EntityGraph.EntityGraphType.LOAD)
    List<Roster> findAll();

    @EntityGraph(value="roster", type= EntityGraph.EntityGraphType.LOAD)
    Optional<Roster> findById(Long id);

    @EntityGraph(value="roster", type= EntityGraph.EntityGraphType.LOAD)
    List<Roster> findByTeamIdAndActiveTrue(Long teamId);

    @EntityGraph(value="roster", type= EntityGraph.EntityGraphType.LOAD)
    List<Roster> findByActiveTrue();
}
