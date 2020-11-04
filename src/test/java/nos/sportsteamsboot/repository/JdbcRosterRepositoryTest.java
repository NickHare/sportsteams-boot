//package nos.sportsteamsboot.repository;
//
//import nos.sportsteams.config.RepositoryTestConfig;
//import nos.sportsteams.model.PlayerModel;
//import nos.sportsteams.model.RosterModel;
//import nos.sportsteams.model.TeamModel;
//import nos.sportsteams.repository.RepositoryTestState;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//
//import java.util.List;
//import java.util.Map;
//
//@SpringJUnitConfig(classes= RepositoryTestConfig.class)
//public class JdbcRosterRepositoryTest {
//    public static final Map<Long, RosterModel> ROSTER_INITIAL_STATE = RepositoryTestState.ROSTER_INITIAL_STATE;
//    public static final Map<Long, RosterModel> ROSTER_INSERT_STATE = RepositoryTestState.ROSTER_INSERT_STATE;
//    public static final RosterModel[] ROSTERS = RepositoryTestState.ROSTERS;
//    public static final RosterModel[] LAZY_ROSTERS = RepositoryTestState.LAZY_ROSTERS;
//    public static final PlayerModel[] PLAYERS = RepositoryTestState.PLAYERS;
//    public static final TeamModel[] TEAMS = RepositoryTestState.TEAMS;
//
//    @Autowired
//    JdbcPlayerRepository jdbcPlayerRepository;
//    @Autowired
//    JdbcTeamRepository jdbcTeamRepository;
//    @Autowired
//    JdbcRosterRepository jdbcRosterRepository;
//
//    @Test
//    @DirtiesContext
//    @DisplayName("Insert null and expect DataIntegrityViolationException")
//    public void insertNullTest(){
//        Assertions.assertThrows(DataIntegrityViolationException.class, ()->this.jdbcRosterRepository.insert(null));
//    }
//
//    @ParameterizedTest
//    @DirtiesContext
//    @MethodSource("rostersExpectingDataIntegrityViolation")
//    @DisplayName("Insert Rosters that expect DataIntegrityViolationException")
//    public void insertExpectingDataIntegrityViolation(RosterModel roster){
//        Assertions.assertThrows(DataIntegrityViolationException.class, ()->this.jdbcRosterRepository.insert(roster));
//    }
//
//    private static List<RosterModel> rostersExpectingDataIntegrityViolation(){
//        return List.of(
//            new RosterModel(null, ROSTERS[3].getTeam()),
//            new RosterModel(ROSTERS[3].getPlayer(), null),
//            new RosterModel(null, ROSTERS[3].getTeamId()),
//            new RosterModel(ROSTERS[3].getPlayerId(), null),
//            ROSTERS[3]
//        );
//    }
//
//    @Test
//    @DirtiesContext
//    @DisplayName("Assert the initial test Rosters")
//    public void initialTest(){
//        verifyState(ROSTER_INITIAL_STATE);
//    }
//
//    @Test
//    @DirtiesContext
//    @DisplayName("Assert the inserted test Rosters")
//    public void insertTest(){
//        RosterModel insertedRoster;
//        RosterModel newRoster1 = ROSTERS[3].withoutIdAndTimestamps();
//        RosterModel newRoster2 = ROSTERS[4].withoutIdAndTimestamps();
//
//        //Assert initial state
//        verifyState(ROSTER_INITIAL_STATE);
//
//        //Insert and assert Roster
//        this.jdbcPlayerRepository.insert(PLAYERS[3].withoutIdAndTimestamps());
//        this.jdbcPlayerRepository.insert(PLAYERS[4].withoutIdAndTimestamps());
//        this.jdbcTeamRepository.insert(TEAMS[2].withoutIdAndTimestamps());
//        insertedRoster = this.jdbcRosterRepository.insert(newRoster1);
//        verifyRoster(ROSTERS[3], insertedRoster);
//        insertedRoster = this.jdbcRosterRepository.insert(newRoster2);
//        verifyRoster(ROSTERS[4], insertedRoster);
//
//        //Assert inserted state
//        verifyState(ROSTER_INSERT_STATE);
//    }
//
//    @Test
//    @DirtiesContext
//    @DisplayName("Assert the lazy inserted test Rosters")
//    public void lazyInsertTest(){
//        RosterModel insertedRoster;
//        RosterModel newRoster1 = LAZY_ROSTERS[3].withoutIdAndTimestamps();
//        RosterModel newRoster2 = LAZY_ROSTERS[4].withoutIdAndTimestamps();
//
//        //Assert initial state
//        verifyState(ROSTER_INITIAL_STATE);
//
//        //Insert and assert Roster
//        this.jdbcPlayerRepository.insert(PLAYERS[3].withoutIdAndTimestamps());
//        this.jdbcPlayerRepository.insert(PLAYERS[4].withoutIdAndTimestamps());
//        this.jdbcTeamRepository.insert(TEAMS[2].withoutIdAndTimestamps());
//        insertedRoster = this.jdbcRosterRepository.insert(newRoster1);
//        verifyLazyRoster(LAZY_ROSTERS[3], insertedRoster);
//        insertedRoster = this.jdbcRosterRepository.insert(newRoster2);
//        verifyLazyRoster(LAZY_ROSTERS[4], insertedRoster);
//
//        //Assert inserted state
//        verifyState(RepositoryTestState.ROSTER_INSERT_STATE);
//    }
//
//    private void verifyState(Map<Long, RosterModel> expectedRosters){
//        //Get all Rosters to compare
//        List<RosterModel> actualRosters =  this.jdbcRosterRepository.selectAll();
//
//        //Compare all Rosters to expected Rosters
//        Assertions.assertEquals(expectedRosters.size(), actualRosters.size());
//        for (RosterModel actualRoster : actualRosters){
//            RosterModel expectedRoster = expectedRosters.get(actualRoster.getId());
//            verifyRoster(expectedRoster, actualRoster);
//        }
//
//        //Compare individual Rosters expected Rosters
//        for (Long id : expectedRosters.keySet()){
//            RosterModel actualRoster = this.jdbcRosterRepository.selectById(id);
//            RosterModel expectedRoster = expectedRosters.get(id);
//            verifyRoster(expectedRoster, actualRoster);
//        }
//    }
//
//    private void verifyRoster(RosterModel expectedRoster, RosterModel actualRoster){
//        //Assertions for every Model (ModelBase)
//        Assertions.assertEquals(expectedRoster.getId(), actualRoster.getId());
//        Assertions.assertNotNull(actualRoster.getCreatedTimestamp());
//        Assertions.assertNotNull(actualRoster.getModifiedTimestamp());
//
//        //Assertions for lazy RosterModel
//        Assertions.assertNotNull(actualRoster.getPlayer());
//        Assertions.assertEquals(expectedRoster.getPlayer().getId(), actualRoster.getPlayer().getId());
//        Assertions.assertNotNull(actualRoster.getPlayerId());
//        Assertions.assertEquals(expectedRoster.getPlayerId(), actualRoster.getPlayerId());
//        Assertions.assertNotNull(actualRoster.getTeam());
//        Assertions.assertEquals(expectedRoster.getTeam().getId(), actualRoster.getTeam().getId());
//        Assertions.assertNotNull(actualRoster.getTeamId());
//        Assertions.assertEquals(expectedRoster.getTeamId(), actualRoster.getTeamId());
//        Assertions.assertTrue(this.jdbcRosterRepository.isPersisted(actualRoster));
//        Assertions.assertTrue(this.jdbcRosterRepository.isLoaded(actualRoster));
//    }
//
//    private void verifyLazyRoster(RosterModel expectedRoster, RosterModel actualRoster){
//        //Assertions for every Model (ModelBase)
//        Assertions.assertEquals(expectedRoster.getId(), actualRoster.getId());
//        Assertions.assertNotNull(actualRoster.getCreatedTimestamp());
//        Assertions.assertNotNull(actualRoster.getModifiedTimestamp());
//
//        //Assertions for lazy RosterModel
//        Assertions.assertNull(actualRoster.getPlayer());
//        Assertions.assertEquals(expectedRoster.getPlayerId(), actualRoster.getPlayerId());
//        Assertions.assertNull(actualRoster.getTeam());
//        Assertions.assertEquals(expectedRoster.getTeamId(), actualRoster.getTeamId());
//        Assertions.assertTrue(this.jdbcRosterRepository.isPersisted(actualRoster));
//        Assertions.assertFalse(this.jdbcRosterRepository.isLoaded(actualRoster));
//    }
//}
