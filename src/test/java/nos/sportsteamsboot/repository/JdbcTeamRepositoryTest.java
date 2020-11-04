//package nos.sportsteamsboot.repository;
//
//import nos.sportsteams.config.RepositoryTestConfig;
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
//public class JdbcTeamRepositoryTest {
//    public static final Map<Long, TeamModel> TEAM_INITIAL_STATE = RepositoryTestState.TEAM_INITIAL_STATE;
//    public static final Map<Long, TeamModel> TEAM_INSERT_STATE = RepositoryTestState.TEAM_INSERT_STATE;
//    public static final TeamModel[] TEAMS = RepositoryTestState.TEAMS;
//
//    @Autowired
//    JdbcTeamRepository jdbcTeamRepository;
//
//    @Test
//    @DirtiesContext
//    @DisplayName("Insert null and expect DataIntegrityViolationException")
//    public void insertNullTest(){
//        Assertions.assertThrows(DataIntegrityViolationException.class, ()->this.jdbcTeamRepository.insert(null));
//    }
//
//    @ParameterizedTest
//    @DirtiesContext
//    @MethodSource("teamsExpectingDataIntegrityViolation")
//    @DisplayName("Insert Teams that expect DataIntegrityViolationException")
//    public void insertExpectingDataIntegrityViolation(TeamModel team){
//        Assertions.assertThrows(DataIntegrityViolationException.class, ()->this.jdbcTeamRepository.insert(team));
//    }
//
//    private static List<TeamModel> teamsExpectingDataIntegrityViolation(){
//        return List.of(
//            new TeamModel(null),
//            TEAMS[2]
//        );
//    }
//
//    @Test
//    @DirtiesContext
//    @DisplayName("Assert the initial test Teams")
//    public void initialTest(){
//        assertState(TEAM_INITIAL_STATE);
//    }
//
//    @Test
//    @DirtiesContext
//    @DisplayName("Assert the inserted test Teams")
//    public void insertTest(){
//        TeamModel insertedTeam;
//        TeamModel newTeam = TEAMS[2].withoutIdAndTimestamps();
//
//        //Assert initial state
//        assertState(TEAM_INITIAL_STATE);
//
//        //Insert Team and assert
//        insertedTeam = this.jdbcTeamRepository.insert(newTeam);
//        assertTeam(TEAMS[2], insertedTeam);
//
//        //Assert inserted insert state
//        assertState(TEAM_INSERT_STATE);
//    }
//
//    private void assertState(Map<Long, TeamModel> expectedTeams){
//        //Get all Teams to compare
//        List<TeamModel> actualTeams =  jdbcTeamRepository.selectAll();
//
//        //Compare all Teams to expected Teams
//        Assertions.assertEquals(expectedTeams.size(), actualTeams.size());
//        for (TeamModel actualTeam : actualTeams){
//            TeamModel expectedTeam = expectedTeams.get(actualTeam.getId());
//            assertTeam(expectedTeam, actualTeam);
//        }
//
//        //Compare individual Teams to expected Teams
//        for (Long id : expectedTeams.keySet()){
//            TeamModel actualTeam = jdbcTeamRepository.selectById(id);
//            TeamModel expectedTeam = expectedTeams.get(id);
//            assertTeam(expectedTeam, actualTeam);
//        }
//    }
//
//    private void assertTeam(TeamModel expectedTeam, TeamModel actualTeam){
//        //Assertions for every Model (ModelBase)
//        Assertions.assertEquals(expectedTeam.getId(), actualTeam.getId());
//        Assertions.assertNotNull(actualTeam.getCreatedTimestamp());
//        Assertions.assertNotNull(actualTeam.getModifiedTimestamp());
//
//        //Assertions for TeamModel
//        Assertions.assertEquals(expectedTeam.getName(), actualTeam.getName());
//        Assertions.assertEquals(this.jdbcTeamRepository.isPersisted(expectedTeam), this.jdbcTeamRepository.isPersisted(actualTeam));
//    }
//}
