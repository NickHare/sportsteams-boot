//package nos.sportsteamsboot.repository;
//
//import nos.sportsteams.config.RepositoryTestConfig;
//import nos.sportsteams.model.GameModel;
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
//public class JdbcGameRepositoryTest {
//    public static final Map<Long, GameModel> GAME_INITIAL_STATE = RepositoryTestState.GAME_INITIAL_STATE;
//    public static final Map<Long, GameModel> GAME_INSERT_STATE = RepositoryTestState.GAME_INSERT_STATE;
//    public static final GameModel[] GAMES = RepositoryTestState.GAMES;
//    public static final GameModel[] LAZY_GAMES = RepositoryTestState.LAZY_GAMES;
//    public static final TeamModel[] TEAMS = RepositoryTestState.TEAMS;
//
//    @Autowired
//    JdbcTeamRepository jdbcTeamRepository;
//    @Autowired
//    JdbcGameRepository jdbcGameRepository;
//
//    @Test
//    @DisplayName("Insert null and expect DataIntegrityViolationException")
//    public void insertNullTest(){
//        Assertions.assertThrows(DataIntegrityViolationException.class, ()->this.jdbcGameRepository.insert(null));
//    }
//
//    @DisplayName("Insert Games that expect DataIntegrityViolationException")
//    @ParameterizedTest
//    @MethodSource("gamesExpectingDataIntegrityViolation")
//    public void insertExpectingDataIntegrityViolationTest(GameModel game){
//        Assertions.assertThrows(DataIntegrityViolationException.class, ()->this.jdbcGameRepository.insert(game));
//    }
//
//    private static List<GameModel> gamesExpectingDataIntegrityViolation(){
//        return List.of(
//                new GameModel(null, GAMES[2].getAwayTeam(), GAMES[2].getHomeScore(), GAMES[2].getAwayScore(), GAMES[2].getStatus(), GAMES[2].getStartTimestamp()),
//                new GameModel(GAMES[2].getHomeTeam(), null, GAMES[2].getHomeScore(), GAMES[2].getAwayScore(), GAMES[2].getStatus(), GAMES[2].getStartTimestamp()),
//                new GameModel(null, GAMES[2].getAwayTeamId(), GAMES[2].getHomeScore(), GAMES[2].getAwayScore(), GAMES[2].getStatus(), GAMES[2].getStartTimestamp()),
//                new GameModel(GAMES[2].getHomeTeamId(), null, GAMES[2].getHomeScore(), GAMES[2].getAwayScore(), GAMES[2].getStatus(), GAMES[2].getStartTimestamp()),
//                GAMES[2]
//        );
//    }
//
//    @Test
//    @DisplayName("Assert the initial test Games")
//    public void initialTest(){
//        verifyState(GAME_INITIAL_STATE);
//    }
//
//    @Test
//    @DisplayName("Assert the inserted test Games")
//    @DirtiesContext
//    public void insertTest(){
//        GameModel insertedGame;
//        GameModel newGame = GAMES[2].withoutIdAndTimestamps();
//
//        //Assert initial state
//        verifyState(GAME_INITIAL_STATE);
//
//        //Insert and assert Roster
//        this.jdbcTeamRepository.insert(TEAMS[2].withoutIdAndTimestamps());
//        insertedGame = this.jdbcGameRepository.insert(newGame);
//        verifyGame(GAMES[2], insertedGame);
//
//        //Assert inserted state
//        verifyState(GAME_INSERT_STATE);
//    }
//
//    @Test
//    @DisplayName("Assert the lazy inserted test Games")
//    @DirtiesContext
//    public void lazyInsertTest(){
//        GameModel insertedGame;
//        GameModel newGame = LAZY_GAMES[2].withoutIdAndTimestamps();
//
//        //Assert initial state
//        verifyState(GAME_INITIAL_STATE);
//
//        //Insert Roster and assert
//        this.jdbcTeamRepository.insert(TEAMS[2].withoutIdAndTimestamps());
//        insertedGame = this.jdbcGameRepository.insert(newGame);
//        verifyLazyGame(LAZY_GAMES[2], insertedGame);
//
//        //Assert inserted state
//        verifyState(GAME_INSERT_STATE);
//    }
//
//    private void verifyState(Map<Long, GameModel> expectedGames){
//        List<GameModel> actualGames =  this.jdbcGameRepository.selectAll();
//
//        Assertions.assertEquals(expectedGames.size(), actualGames.size());
//        for (GameModel actualGame : actualGames){
//            GameModel expectedGame = expectedGames.get(actualGame.getId());
//            verifyGame(expectedGame, actualGame);
//        }
//        for (Long id : expectedGames.keySet()){
//            GameModel actualGame = this.jdbcGameRepository.selectById(id);
//            GameModel expectedGame = expectedGames.get(id);
//            verifyGame(expectedGame, actualGame);
//        }
//    }
//
//    private void verifyGame(GameModel expectedGame, GameModel actualGame){
//        //Assertions for every Model (ModelBase)
//        Assertions.assertEquals(expectedGame.getId(), actualGame.getId());
//        Assertions.assertNotNull(actualGame.getCreatedTimestamp());
//        Assertions.assertNotNull(actualGame.getModifiedTimestamp());
//
//        //Assertions for GameModel
//        Assertions.assertNotNull(actualGame.getHomeTeam());
//        Assertions.assertEquals(expectedGame.getHomeTeam().getId(), actualGame.getHomeTeam().getId());
//        Assertions.assertNotNull(actualGame.getHomeTeamId());
//        Assertions.assertEquals(expectedGame.getHomeTeamId(), actualGame.getHomeTeamId());
//        Assertions.assertNotNull(actualGame.getAwayTeam());
//        Assertions.assertEquals(expectedGame.getAwayTeam().getId(), actualGame.getAwayTeam().getId());
//        Assertions.assertNotNull(actualGame.getAwayTeamId());
//        Assertions.assertEquals(expectedGame.getAwayTeamId(), actualGame.getAwayTeamId());
//        Assertions.assertEquals(expectedGame.getHomeScore(), actualGame.getHomeScore());
//        Assertions.assertEquals(expectedGame.getAwayScore(), actualGame.getAwayScore());
//        Assertions.assertEquals(expectedGame.getStatus(), actualGame.getStatus());
//        Assertions.assertNotNull(actualGame.getCreatedTimestamp());
//        Assertions.assertNotNull(actualGame.getModifiedTimestamp());
//        Assertions.assertTrue(this.jdbcGameRepository.isPersisted(actualGame));
//        Assertions.assertTrue(this.jdbcGameRepository.isLoaded(actualGame));
//    }
//
//    private void verifyLazyGame(GameModel expectedGame, GameModel actualGame){
//        //Assertions for every Model (ModelBase)
//        Assertions.assertEquals(expectedGame.getId(), actualGame.getId());
//        Assertions.assertNotNull(actualGame.getCreatedTimestamp());
//        Assertions.assertNotNull(actualGame.getModifiedTimestamp());
//
//        //Assertions for lazy GameModel
//        Assertions.assertNull(actualGame.getHomeTeam());
//        Assertions.assertEquals(expectedGame.getHomeTeamId(), actualGame.getHomeTeamId());
//        Assertions.assertNull(actualGame.getAwayTeam());
//        Assertions.assertEquals(expectedGame.getAwayTeamId(), actualGame.getAwayTeamId());
//        Assertions.assertEquals(expectedGame.getAwayTeamId(), actualGame.getAwayTeamId());
//        Assertions.assertEquals(expectedGame.getHomeScore(), actualGame.getHomeScore());
//        Assertions.assertEquals(expectedGame.getAwayScore(), actualGame.getAwayScore());
//        Assertions.assertEquals(expectedGame.getStatus(), actualGame.getStatus());
//        Assertions.assertNotNull(actualGame.getCreatedTimestamp());
//        Assertions.assertNotNull(actualGame.getModifiedTimestamp());
//        Assertions.assertTrue(this.jdbcGameRepository.isPersisted(actualGame));
//        Assertions.assertFalse(this.jdbcGameRepository.isLoaded(actualGame));
//    }
//}
