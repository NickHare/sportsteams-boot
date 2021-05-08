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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DataJpaTest
@EnableJpaAuditing
@Sql({"/destroy.sql", "/schema.sql", "/data.sql"})
public class PlayerRepositoryTest {
    public static final Player[] PLAYERS = new Player[]{
            new Player(1L, null, "Alex", null, null),
            new Player(2L, null, "Dan", null, null),
            new Player(3L, null, "Nick", null, null),
            new Player(4L, null, "Aaron", null, null),
            new Player(5L, null, "Rory", null, null),
    };

    public static final Map<Long, Player> PLAYER_INITIAL_STATE = new HashMap<>();
    static {
        PLAYER_INITIAL_STATE.put(1L, PLAYERS[0]);
        PLAYER_INITIAL_STATE.put(2L, PLAYERS[1]);
        PLAYER_INITIAL_STATE.put(3L, PLAYERS[2]);
    }

    public static final Map<Long, Player> PLAYER_INSERTED_STATE = new HashMap<>();
    static {
        PLAYER_INSERTED_STATE.put(1L, PLAYERS[0]);
        PLAYER_INSERTED_STATE.put(2L, PLAYERS[1]);
        PLAYER_INSERTED_STATE.put(3L, PLAYERS[2]);
        PLAYER_INSERTED_STATE.put(4L, PLAYERS[3]);
        PLAYER_INSERTED_STATE.put(5L, PLAYERS[4]);
    }

    @Autowired PlayerRepository playerRepository;

    @Test
    @DisplayName("Insert null and expect DataAccessException")
    public void insertNullTest(){
        Assertions.assertThrows(DataAccessException.class, ()->this.playerRepository.save(null));
    }

    @ParameterizedTest
    @MethodSource("playersExpectingDataAccessException")
    @DisplayName("Insert Players that expect DataAccessException")
    public void insertExpectingDataAccessException(Player player) {
        Assertions.assertThrows(DataAccessException.class, ()->this.playerRepository.save(player));
    }

    private static List<Player> playersExpectingDataAccessException(){
        return List.of(
                Player.NullPlayer
        );
    }

    @Test
    @DisplayName("Assert the initial test Players")
    public void initialTest(){
        assertPlayerState(playerRepository, PLAYER_INITIAL_STATE);
    }

    @Test
    @Rollback
    @DisplayName("Assert the inserted test Players")
    public void insertTest(){
        Player insertedPlayer;
        Player newPlayer1 = new Player(PLAYERS[3]);
        Player newPlayer2 = new Player(PLAYERS[4]);

        //Assert initial state
        PlayerRepositoryTest.assertPlayerState(playerRepository, PLAYER_INITIAL_STATE);

        //Insert Players and assert
        insertedPlayer = this.playerRepository.save(newPlayer1);
        PlayerRepositoryTest.assertPlayer(PLAYERS[3], insertedPlayer);
        insertedPlayer = this.playerRepository.save(newPlayer2);
        PlayerRepositoryTest.assertPlayer(PLAYERS[4], insertedPlayer);
        this.playerRepository.flush();

        //Assert inserted state
        PlayerRepositoryTest.assertPlayerState(playerRepository, PLAYER_INSERTED_STATE);
    }

    public static void assertPlayerState(PlayerRepository playerRepository, Map<Long, Player> expectedPlayers){
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

    public static void assertPlayer(Player expectedPlayer, Player actualPlayer){
        //Assertions for every Model (ModelBase)
        Assertions.assertEquals(expectedPlayer.getId(), actualPlayer.getId());
        Assertions.assertEquals(expectedPlayer.getExternalId(), actualPlayer.getExternalId());
        Assertions.assertNotNull(actualPlayer.getCreatedTimestamp());
        Assertions.assertNotNull(actualPlayer.getModifiedTimestamp());

        //Assertions for Player
        Assertions.assertEquals(expectedPlayer.getName(), actualPlayer.getName());
    }

}
