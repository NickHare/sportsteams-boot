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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DataJpaTest
@EnableJpaAuditing
@Sql({"/destroy.sql", "/schema.sql", "/data.sql"})
public class RosterRepositoryTest {
    public static final Player[] PLAYERS = PlayerRepositoryTest.PLAYERS;
    public static final Team[] TEAMS = TeamRepositoryTest.TEAMS;
    public static final Roster[] ROSTERS = new Roster[]{
            new Roster(1L, null, PLAYERS[0], TEAMS[0], true, null, null),
            new Roster(2L, null, PLAYERS[1], TEAMS[0], true, null, null),
            new Roster(3L, null, PLAYERS[2], TEAMS[1], true, null, null),
            new Roster(4L, null, PLAYERS[3], TEAMS[2], true, null, null),
            new Roster(5L, null, PLAYERS[4], TEAMS[2], true, null, null),
    };

    public static final Map<Long, Roster> ROSTER_INITIAL_STATE = new HashMap<>();
    static {
        ROSTER_INITIAL_STATE.put(1L, ROSTERS[0]);
        ROSTER_INITIAL_STATE.put(2L, ROSTERS[1]);
        ROSTER_INITIAL_STATE.put(3L, ROSTERS[2]);
    }

    public static final Map<Long, Roster> ROSTER_INSERT_STATE = new HashMap<>();
    static {
        ROSTER_INSERT_STATE.put(1L, ROSTERS[0]);
        ROSTER_INSERT_STATE.put(2L, ROSTERS[1]);
        ROSTER_INSERT_STATE.put(3L, ROSTERS[2]);
        ROSTER_INSERT_STATE.put(4L, ROSTERS[3]);
        ROSTER_INSERT_STATE.put(5L, ROSTERS[4]);
    }

    @Autowired PlayerRepository playerRepository;
    @Autowired TeamRepository teamRepository;
    @Autowired RosterRepository rosterRepository;

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
                Roster.NullRoster
        );
    }

    @Test
    @DisplayName("Assert the initial test Rosters")
    public void initialTest(){
        RosterRepositoryTest.assertRosterState(rosterRepository, ROSTER_INITIAL_STATE);
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
        RosterRepositoryTest.assertRosterState(rosterRepository, ROSTER_INITIAL_STATE);

        //Insert dependant Players and Teams
        this.playerRepository.save(newPlayer1);
        this.playerRepository.save(newPlayer2);
        this.playerRepository.flush();
        this.teamRepository.save(newTeam);
        this.teamRepository.flush();

        //Insert Rosters and assert
        insertedRoster = this.rosterRepository.save(newRoster1);
        RosterRepositoryTest.assertRoster(ROSTERS[3], insertedRoster);
        insertedRoster = this.rosterRepository.save(newRoster2);
        RosterRepositoryTest.assertRoster(ROSTERS[4], insertedRoster);
        this.rosterRepository.flush();

        //Assert inserted state
        RosterRepositoryTest.assertRosterState(rosterRepository, ROSTER_INSERT_STATE);
    }

    public static void assertRosterState(RosterRepository rosterRepository, Map<Long, Roster> expectedRosters){
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

    public static void assertRoster(Roster expectedRoster, Roster actualRoster){
        //Assertions for every Model (ModelBase)
        Assertions.assertEquals(expectedRoster.getId(), actualRoster.getId());
        Assertions.assertEquals(expectedRoster.getExternalId(), actualRoster.getExternalId());
        Assertions.assertNotNull(actualRoster.getCreatedTimestamp());
        Assertions.assertNotNull(actualRoster.getModifiedTimestamp());

        //Assertions for Roster
        PlayerRepositoryTest.assertPlayer(expectedRoster.getPlayer(), actualRoster.getPlayer());
        TeamRepositoryTest.assertTeam(expectedRoster.getTeam(), actualRoster.getTeam());
        Assertions.assertEquals(expectedRoster.getActive(), actualRoster.getActive());
    }
}
