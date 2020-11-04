package nos.sportsteamsboot.repository;

import nos.sportsteamsboot.model.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class RepositoryTestState {
    public static final Long EPOCH_MIDNIGHT_2020 = 1577836800000L;
    public static final Long EPOCH_5_MINUTES = 5L * 60L * 1000L;//300,000
    public static final Long EPOCH_1_HOUR = 60L * 60L * 1000L;//3,600,000

    public static final Player[] PLAYERS = new Player[]{
            new Player(1L, null, "Alex", null, null),
            new Player(2L, null, "Dan", null, null),
            new Player(3L, null, "Nick", null, null),
            new Player(4L, null, "Aaron", null, null),
            new Player(5L, null, "Rory", null, null),
    };
    public static final Team[] TEAMS = new Team[]{
            new Team(1L, null, "Easy Company", null, null),
            new Team(2L, null, "UnEasy Company", null, null),
            new Team(3L, null, "Dragons", null, null),
    };
    public static final Roster[] ROSTERS = new Roster[]{
            new Roster(1L, null, 1L,1L, null, null),
            new Roster(2L, null, 2L,1L, null, null),
            new Roster(3L, null, 3L,2L, null, null),
            new Roster(4L, null, 4L,3L, null, null),
            new Roster(5L, null, 5L,3L, null, null),
    };

    public static final Game[] GAMES = new Game[]{
            new Game(1L, null, 1L, 2L, 2, 1, "COMPLETED", new Timestamp(EPOCH_MIDNIGHT_2020), null, null),
            new Game(2L, null, 2L, 1L, 0, 0, "IN_PROGRESS", new Timestamp(EPOCH_MIDNIGHT_2020 + EPOCH_1_HOUR), null, null),
            new Game(3L, null, 3L, 1L, 0, 0, "SCHEDULED", new Timestamp(EPOCH_MIDNIGHT_2020 + 2*EPOCH_1_HOUR), null, null),
    };
//    public static final Event[] EVENTS = new Event[]{
//            new Event(1L, 1L, "GOAL", "Alex scores.", "", new Timestamp(EPOCH_MIDNIGHT_2020 + EPOCH_5_MINUTES)),
//            new Event(2L, 2L, "GOAL", "Dan scores.", "", new Timestamp(EPOCH_MIDNIGHT_2020 + 2*EPOCH_5_MINUTES)),
//            new Event(3L, 1L, "PENALTY", "Nick receives a penalty.", "", new Timestamp(EPOCH_MIDNIGHT_2020 + 3*EPOCH_5_MINUTES)),
//            new Event(4L, 3L, "GOAL", "Nick scores.", "", new Timestamp(EPOCH_MIDNIGHT_2020 + 4*EPOCH_5_MINUTES)),
//            new Event(5L, 4L, "GOAL", "Alex scores.", "", new Timestamp(EPOCH_MIDNIGHT_2020 + EPOCH_1_HOUR + EPOCH_5_MINUTES)),
//            new Event(6L, 5L, "GOAL", "Dan scores.", "", new Timestamp(EPOCH_MIDNIGHT_2020 + EPOCH_1_HOUR + 2*EPOCH_5_MINUTES)),
//            new Event(7L, 6L, "GOAL", "Aaron scores.", "", new Timestamp(EPOCH_MIDNIGHT_2020 + EPOCH_1_HOUR + 3*EPOCH_5_MINUTES)),
//            new Event(8L, 7L, "GOAL", "Rory scores.", "", new Timestamp(EPOCH_MIDNIGHT_2020 + EPOCH_1_HOUR + 4*EPOCH_5_MINUTES)),
//    };

    public static final Map<Long, Player> PLAYER_INITIAL_STATE = new HashMap<>();
    static {
        PLAYER_INITIAL_STATE.put(1L, PLAYERS[0]);
        PLAYER_INITIAL_STATE.put(2L, PLAYERS[1]);
        PLAYER_INITIAL_STATE.put(3L, PLAYERS[2]);
    }

    public static final Map<Long, Player> PLAYER_INSERT_STATE = new HashMap<>();
    static {
        PLAYER_INSERT_STATE.put(1L, PLAYERS[0]);
        PLAYER_INSERT_STATE.put(2L, PLAYERS[1]);
        PLAYER_INSERT_STATE.put(3L, PLAYERS[2]);
        PLAYER_INSERT_STATE.put(4L, PLAYERS[3]);
        PLAYER_INSERT_STATE.put(5L, PLAYERS[4]);
    }

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

    public static final Map<Long, Game> GAME_INITIAL_STATE = new HashMap<>();
    static {
        GAME_INITIAL_STATE.put(1L, GAMES[0]);
        GAME_INITIAL_STATE.put(2L, GAMES[1]);
    }

    public static final Map<Long, Game> GAME_INSERT_STATE = new HashMap<>();
    static {
        GAME_INSERT_STATE.put(1L, GAMES[0]);
        GAME_INSERT_STATE.put(2L, GAMES[1]);
        GAME_INSERT_STATE.put(3L, GAMES[2]);
    }
    
//    public static final Map<Long, Event> EVENT_INITIAL_STATE = new HashMap<>();
//    static {
//        EVENT_INITIAL_STATE.put(1L, EVENTS[0]);
//        EVENT_INITIAL_STATE.put(2L, EVENTS[1]);
//        EVENT_INITIAL_STATE.put(3L, EVENTS[2]);
//        EVENT_INITIAL_STATE.put(4L, EVENTS[3]);
//    }
//
//    public static final Map<Long, Event> EVENT_INSERT_STATE = new HashMap<>();
//    static {
//        EVENT_INSERT_STATE.put(1L, EVENTS[0]);
//        EVENT_INSERT_STATE.put(2L, EVENTS[1]);
//        EVENT_INSERT_STATE.put(3L, EVENTS[2]);
//        EVENT_INSERT_STATE.put(4L, EVENTS[3]);
//        EVENT_INSERT_STATE.put(5L, EVENTS[4]);
//        EVENT_INSERT_STATE.put(6L, EVENTS[5]);
//        EVENT_INSERT_STATE.put(7L, EVENTS[6]);
//        EVENT_INSERT_STATE.put(8L, EVENTS[7]);
//    }
}
