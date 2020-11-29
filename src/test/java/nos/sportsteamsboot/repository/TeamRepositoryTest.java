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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;

@DataJpaTest
@EnableJpaAuditing
@Sql({"/destroy.sql", "/schema.sql", "/data.sql"})
public class TeamRepositoryTest {
    public static final Map<Long, Team> TEAM_INITIAL_STATE = RepositoryTestState.TEAM_INITIAL_STATE;
    public static final Map<Long, Team> TEAM_INSERT_STATE = RepositoryTestState.TEAM_INSERT_STATE;
    public static final Team[] TEAMS = RepositoryTestState.TEAMS;

    @Autowired TeamRepository teamRepository;
    @Autowired JdbcTemplate jdbcTemplate;

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
                new Team(null, null, null, null, null)
        );
    }

    @Test
    @DisplayName("Assert the initial test Teams")
    public void initialTest(){
        assertState(TEAM_INITIAL_STATE);
    }

    @Test
    @Rollback
    @DisplayName("Assert the inserted test Teams")
    public void insertTest(){
        Team insertedTeam;
        Team newTeam1 = new Team(TEAMS[2]);

        //Assert initial state
        assertState(TEAM_INITIAL_STATE);

        //Insert Teams and assert
        insertedTeam = this.teamRepository.saveAndFlush(newTeam1);
        assertTeam(TEAMS[2], insertedTeam);

        //Assert inserted state
        assertState(TEAM_INSERT_STATE);
    }

    public void assertState(Map<Long, Team> expectedTeams){
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

    public void assertTeam(Team expectedTeam, Team actualTeam){
        //Assertions for every Model (ModelBase)
        Assertions.assertEquals(expectedTeam.getId(), actualTeam.getId());
        Assertions.assertEquals(expectedTeam.getExternalId(), actualTeam.getExternalId());
        Assertions.assertNotNull(actualTeam.getCreatedTimestamp());
        Assertions.assertNotNull(actualTeam.getModifiedTimestamp());

        //Assertions for Team
        Assertions.assertEquals(expectedTeam.getName(), actualTeam.getName());
    }

//            this.teamRepository.save(team);
//            List<Team> teams = this.teamRepository.findAll();
//            List<Team> p = jdbcTemplate.query("SELECT * FROM team;", new RowMapper<Team>(){
//                public Team mapRow(ResultSet rs, int r) throws SQLException {
//                    return new Team(
//                            rs.getLong("id"),
//                            rs.getString("external_id"),
//                            rs.getString("name"),
//                            rs.getTimestamp("created_timestamp"),
//                            rs.getTimestamp("modified_timestamp")
//                    );
//                }
//            });
//            System.out.println("?");
//        });
}
