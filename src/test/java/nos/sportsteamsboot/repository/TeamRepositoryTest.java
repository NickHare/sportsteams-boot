package nos.sportsteamsboot.repository;

import nos.sportsteamsboot.model.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DataJpaTest
@EnableJpaAuditing
@Sql({"/destroy.sql", "/schema.sql", "/data.sql"})
public class TeamRepositoryTest {
    public static final Team[] TEAMS = new Team[]{
            new Team(1L, null, "Easy Company", null, null, null),
            new Team(2L, null, "UnEasy Company", null , null, null),
            new Team(3L, null, "Dragons", null , null, null),
    };

    public static final Map<Long, Team> TEAM_INITIAL_STATE = new HashMap<>();
    static {
        TEAM_INITIAL_STATE.put(1L, TEAMS[0]);
        TEAM_INITIAL_STATE.put(2L, TEAMS[1]);
    }

    public static final Map<Long, Team> TEAM_INSERT_STATE = new HashMap<>();
    static {
        TEAM_INSERT_STATE.put(1L, TEAMS[0]);
        TEAM_INSERT_STATE.put(2L, TEAMS[1]);
        TEAM_INSERT_STATE.put(3L, TEAMS[2]);
    }

    @Autowired TeamRepository teamRepository;

    @Test
    @DisplayName("Insert null and expect DataIntegrityViolationException")
    public void insertNullTest(){
        Assertions.assertThrows(DataAccessException.class, ()->this.teamRepository.save(null));
    }

    @ParameterizedTest
    @MethodSource("teamsExpectingDataAccessException")
    @DisplayName("Insert Teams that expect DataAccessException")
    public void insertExpectingDataAccessException(Team team) {
        Assertions.assertThrows(DataAccessException.class, ()->this.teamRepository.save(team));
    }

    private static List<Team> teamsExpectingDataAccessException(){
        return List.of(
                new Team(null, null, null, null , null, null)
        );
    }

    @Test
    @DisplayName("Assert the initial test Teams")
    public void initialTest(){
        TeamRepositoryTest.assertTeamState(teamRepository, TEAM_INITIAL_STATE);
    }

    @Test
    @Rollback
    @DisplayName("Assert the inserted test Teams")
    public void insertTest(){
        Team insertedTeam;
        Team newTeam1 = new Team(TEAMS[2]);

        //Assert initial state
        TeamRepositoryTest.assertTeamState(teamRepository, TEAM_INITIAL_STATE);

        //Insert Teams and assert
        insertedTeam = this.teamRepository.save(newTeam1);
        this.teamRepository.flush();
        TeamRepositoryTest.assertTeam(TEAMS[2], insertedTeam);

        //Assert inserted state
        TeamRepositoryTest.assertTeamState(teamRepository, TEAM_INSERT_STATE);
    }

    public static void assertTeamState(TeamRepository teamRepository, Map<Long, Team> expectedTeams){
        //Get all Teams to compare
        List<Team> actualTeams =  teamRepository.findAll();

        //Compare all Teams to expected Teams
        Assertions.assertEquals(expectedTeams.size(), actualTeams.size());
        for (Team actualTeam : actualTeams){
            Team expectedTeam = expectedTeams.get(actualTeam.getId());
            assertTeam(expectedTeam, actualTeam);
        }

        //Compare individual Teams expected Teams
        for (Long id : expectedTeams.keySet()){
            Team actualTeam = teamRepository.findById(id).orElseThrow();
            Team expectedTeam = expectedTeams.get(id);
            assertTeam(expectedTeam, actualTeam);
        }
    }

    public static void assertTeam(Team expectedTeam, Team actualTeam){
        //Assertions for every Model (ModelBase)
        Assertions.assertEquals(expectedTeam.getId(), actualTeam.getId());
        Assertions.assertEquals(expectedTeam.getExternalId(), actualTeam.getExternalId());
        Assertions.assertNotNull(actualTeam.getCreatedTimestamp());
        Assertions.assertNotNull(actualTeam.getModifiedTimestamp());

        //Assertions for Team
        Assertions.assertEquals(expectedTeam.getName(), actualTeam.getName());
    }
}
