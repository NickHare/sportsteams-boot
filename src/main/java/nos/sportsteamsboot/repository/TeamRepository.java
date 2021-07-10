package nos.sportsteamsboot.repository;

import nos.sportsteamsboot.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findById(Long id);
    Optional<Team> findByName(String name);
    Optional<Team> findByExternalId(String extId);
    List<Team> findAll();
}
