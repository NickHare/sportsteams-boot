package nos.sportsteamsboot.repository;

import nos.sportsteamsboot.model.Player;
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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.DirtiesContext;

import javax.validation.ConstraintViolationException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@DataJpaTest
@EnableJpaAuditing
public class PlayerRepositoryTest {
    public static final Map<Long, Player> PLAYER_INITIAL_STATE = RepositoryTestState.PLAYER_INITIAL_STATE;
    public static final Map<Long, Player> PLAYER_INSERT_STATE = RepositoryTestState.PLAYER_INSERT_STATE;
    public static final Player[] PLAYERS = RepositoryTestState.PLAYERS;

    @Autowired PlayerRepository playerRepository;
    @Autowired JdbcTemplate jdbcTemplate;

    @Test
    @DirtiesContext
    @DisplayName("Insert null and expect DataIntegrityViolationException")
    public void insertNullTest(){
        Assertions.assertThrows(DataAccessException.class, ()->this.playerRepository.save(null));
    }

    @ParameterizedTest
    @DirtiesContext
    @MethodSource("playersExpectingConstraintViolationException")
    @DisplayName("Insert Players that expect ConstraintViolationException")
    public void insertExpectingConstraintViolationException(Player player) {
        Assertions.assertThrows(ConstraintViolationException.class, ()->this.playerRepository.save(player));
//            this.playerRepository.save(player);
//            List<Player> players = this.playerRepository.findAll();
//            List<Player> p = jdbcTemplate.query("SELECT * FROM player;", new RowMapper<Player>(){
//                public Player mapRow(ResultSet rs, int r) throws SQLException {
//                    return new Player(
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

    private static List<Player> playersExpectingConstraintViolationException(){
        return List.of(
                Player.EmptyPlayer,
                new Player(null, null, null, null, null)
        );
    }

    @Test
    @DirtiesContext
    @DisplayName("Assert the initial test Players")
    public void initialTest(){
        assertState(PLAYER_INITIAL_STATE);
    }

    @Test
    @DirtiesContext
    @DisplayName("Assert the inserted test Players")
    public void insertTest(){
        Player insertedPlayer;
        Player newPlayer1 = new Player(PLAYERS[3]);
        Player newPlayer2 = new Player(PLAYERS[4]);
        newPlayer1.setId(null);
        newPlayer1.setCreatedTimestamp(null);
        newPlayer1.setModifiedTimestamp(null);
        newPlayer2.setId(null);
        newPlayer2.setCreatedTimestamp(null);
        newPlayer2.setModifiedTimestamp(null);

        //Assert initial state
        assertState(PLAYER_INITIAL_STATE);

        //Insert Players and assert
        insertedPlayer = this.playerRepository.saveAndFlush(newPlayer1);
        assertPlayer(PLAYERS[3], insertedPlayer);
        insertedPlayer = this.playerRepository.save(newPlayer2);
        assertPlayer(PLAYERS[4], insertedPlayer);

        //Assert inserted state
        assertState(PLAYER_INSERT_STATE);
    }

    public void assertState(Map<Long, Player> expectedPlayers){
        //Get all Players to compare
        List<Player> actualPlayers =  playerRepository.findAll();

        //Compare all Players to expected Players
        Assertions.assertEquals(expectedPlayers.size(), actualPlayers.size());
        for (Player actualPlayer : actualPlayers){
            Player expectedPlayer = expectedPlayers.get(actualPlayer.getId());
            assertPlayer(expectedPlayer, actualPlayer);
        }

        //Compare individual Players expected Players
        for (Long id : expectedPlayers.keySet()){
            Player actualPlayer = playerRepository.findById(id).orElseThrow();
            Player expectedPlayer = expectedPlayers.get(id);
            assertPlayer(expectedPlayer, actualPlayer);
        }
    }

    public void assertPlayer(Player expectedPlayer, Player actualPlayer){
        //Assertions for every Model (ModelBase)
        Assertions.assertEquals(expectedPlayer.getId(), actualPlayer.getId());
        Assertions.assertEquals(expectedPlayer.getExternalId(), actualPlayer.getExternalId());
        Assertions.assertNotNull(actualPlayer.getCreatedTimestamp());
        Assertions.assertNotNull(actualPlayer.getModifiedTimestamp());

        //Assertions for Player
        Assertions.assertEquals(expectedPlayer.getName(), actualPlayer.getName());
    }
}
