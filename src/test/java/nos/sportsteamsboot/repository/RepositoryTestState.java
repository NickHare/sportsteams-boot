package nos.sportsteamsboot.repository;

import nos.sportsteamsboot.model.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class RepositoryTestState {
    public static final Long FIRST_MIDNIGHT_2020 = 1577836800000L;
    public static final Long DELTA_5_MINUTES = 5L * 60L * 1000L;//300,000
    public static final Long DELTA_1_HOUR = 60L * 60L * 1000L;//3,600,000

    public static final Game[] GAMES = new Game[]{
            new Game(1L, null, 1L, 2L, 2, 1, "COMPLETED", new Timestamp(FIRST_MIDNIGHT_2020), null, null),
            new Game(2L, null, 2L, 1L, 0, 0, "IN_PROGRESS", new Timestamp(FIRST_MIDNIGHT_2020 + DELTA_1_HOUR), null, null),
            new Game(3L, null, 3L, 1L, 0, 0, "SCHEDULED", new Timestamp(FIRST_MIDNIGHT_2020 + 2* DELTA_1_HOUR), null, null),
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
