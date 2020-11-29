package nos.sportsteamsboot.repository;

import nos.sportsteamsboot.model.Player;
import nos.sportsteamsboot.model.Roster;
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
public class RosterRepositoryTest {
    public static final Map<Long, Roster> ROSTER_INITIAL_STATE = RepositoryTestState.ROSTER_INITIAL_STATE;
    public static final Map<Long, Roster> ROSTER_INSERT_STATE = RepositoryTestState.ROSTER_INSERT_STATE;
    public static final Player[] PLAYERS = RepositoryTestState.PLAYERS;
    public static final Team[] TEAMS = RepositoryTestState.TEAMS;
    public static final Roster[] ROSTERS = RepositoryTestState.ROSTERS;

    @Autowired PlayerRepository playerRepository;
    @Autowired TeamRepository teamRepository;
    @Autowired RosterRepository rosterRepository;
    @Autowired JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("Insert null and expect DataIntegrityViolationException")
    public void insertNullTest(){
        Assertions.assertThrows(DataAccessException.class, ()->this.rosterRepository.save(null));
    }

    @ParameterizedTest
    @MethodSource("rostersExpectingDataAccessException")
    @DisplayName("Insert Rosters that expect DataAccessException")
    public void insertExpectingDataAccessException(Roster roster) {
        Assertions.assertThrows(DataAccessException.class, ()->this.rosterRepository.save(roster));
    }

    private static List<Roster> rostersExpectingDataAccessException(){
        return List.of(
                Roster.EmptyRoster,
                new Roster(null, null, null, null, null, null)
        );
    }

    @Test
    @DisplayName("Assert the initial test Rosters")
    public void initialTest(){
        assertState(ROSTER_INITIAL_STATE);
    }

    @Test
    @Rollback
    @DisplayName("Assert the inserted test Rosters")
    public void insertTest(){
        Roster insertedRoster;

        Player newPlayer1 = new Player(PLAYERS[3]);
        Player newPlayer2 = new Player(PLAYERS[4]);
        Team newTeam = new Team(TEAMS[2]);
        Roster newRoster1 = new Roster(ROSTERS[3]);
        Roster newRoster2 = new Roster(ROSTERS[4]);

        //Assert initial state
        assertState(ROSTER_INITIAL_STATE);

        //Insert dependant Players and Teams
        this.playerRepository.save(newPlayer1);
        this.playerRepository.save(newPlayer2);
        this.teamRepository.save(newTeam);

        //Insert Rosters and assert
        insertedRoster = this.rosterRepository.saveAndFlush(newRoster1);
        assertRoster(ROSTERS[3], insertedRoster);
        insertedRoster = this.rosterRepository.saveAndFlush(newRoster2);
        assertRoster(ROSTERS[4], insertedRoster);

        //Assert inserted state
        assertState(ROSTER_INSERT_STATE);
    }

    public void assertState(Map<Long, Roster> expectedRosters){
        //Get all Rosters to compare
        List<Roster> actualRosters =  rosterRepository.findAll();

        //Compare all Rosters to expected Rosters
        Assertions.assertEquals(expectedRosters.size(), actualRosters.size());
        for (Roster actualRoster : actualRosters){
            Roster expectedRoster = expectedRosters.get(actualRoster.getId());
            assertRoster(expectedRoster, actualRoster);
        }

        //Compare individual Rosters expected Rosters
        for (Long id : expectedRosters.keySet()){
            Roster actualRoster = rosterRepository.findById(id).orElseThrow();
            Roster expectedRoster = expectedRosters.get(id);
            assertRoster(expectedRoster, actualRoster);
        }
    }

    public void assertRoster(Roster expectedRoster, Roster actualRoster){
        //Assertions for every Model (ModelBase)
        Assertions.assertEquals(expectedRoster.getId(), actualRoster.getId());
        Assertions.assertEquals(expectedRoster.getExternalId(), actualRoster.getExternalId());
        Assertions.assertNotNull(actualRoster.getCreatedTimestamp());
        Assertions.assertNotNull(actualRoster.getModifiedTimestamp());

        //Assertions for Roster
        Assertions.assertEquals(expectedRoster.getPlayerId(), actualRoster.getPlayerId());
        Assertions.assertEquals(expectedRoster.getId(), actualRoster.getId());
    }

//            this.rosterRepository.save(roster);
//            List<Roster> rosters = this.rosterRepository.findAll();
//            List<Roster> p = jdbcTemplate.query("SELECT * FROM roster;", new RowMapper<Roster>(){
//                public Roster mapRow(ResultSet rs, int r) throws SQLException {
//                    return new Roster(
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
