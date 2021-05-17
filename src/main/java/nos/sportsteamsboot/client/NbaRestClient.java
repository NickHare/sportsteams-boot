package nos.sportsteamsboot.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nos.sportsteamsboot.model.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NbaRestClient {
    //URL Parameters
    private static final String LEAGUE_PARAM = "LeagueId";
    private static final String LEAGUE_VALUE = "00";
    private static final String TEAM_PARAM = "TeamId";

    //URL Templates
    private static final String TEAM_LIST_URL = "https://stats.nba.com/stats/commonteamyears?" + LEAGUE_PARAM + "={" + LEAGUE_PARAM + "}";
    private static final String TEAM_INFO_URL = "https://stats.nba.com/stats/teamdetails?" + TEAM_PARAM + "={"+ TEAM_PARAM + "}";

    private static final Map<String, String> TEAM_MAP = Map.ofEntries(
//            Map.entry("Raptors", "1610612761"),
            Map.entry("Lakers", "1610612747"),
            Map.entry("Heat", "1610612748")
    );
    private static final String SEASON = "2019-20";

    private ObjectMapper objectMapper;
    private RestTemplate restTemplate;

    public NbaRestClient(RestTemplateBuilder builder){
        this.objectMapper = new ObjectMapper();
        this.restTemplate = builder.build();
    }

    public List<Long> fetchTeamIdList(){
        HttpEntity<String> request = setupRequest();
        Map<String, String> urlParameters = Map.of(LEAGUE_PARAM, LEAGUE_VALUE);
        ResponseEntity<String> response = this.restTemplate.exchange(TEAM_LIST_URL, HttpMethod.GET, request, String.class, urlParameters);
        try {
            JsonNode responseJson = this.objectMapper.readTree(response.getBody());
            return parseTeamIds(responseJson);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private List<Long> parseTeamIds(JsonNode responseJson){
        JsonNode teamListJson = responseJson
                .get("resultSets")
                .get(0)
                .get("rowSet");
        List<Long> teamIds = new ArrayList<>();
        for (JsonNode teamJson  : teamListJson){
            Long teamId = teamJson.get(1).asLong();
            String teamAbbrev = teamJson.get(4).textValue();
            if (teamAbbrev != null) {
                teamIds.add(teamId);
            }
        }
        return teamIds;
    }

    public Team fetchTeam(Long teamId){
        HttpEntity<String> request = setupRequest();
        Map<String, String> urlParameters = Map.of(TEAM_PARAM, teamId.toString());
        ResponseEntity<String> response = this.restTemplate.exchange(TEAM_INFO_URL, HttpMethod.GET, request, String.class, urlParameters);
        try {
            JsonNode responseJson = this.objectMapper.readTree(response.getBody());
            return parseTeam(responseJson);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private Team parseTeam(JsonNode responseJson){
        JsonNode teamJson = responseJson
                .get("resultSets")
                .get(0)
                .get("rowSet")
                .get(0);
        String extId = teamJson.get(0).asText();
        String name = teamJson.get(2).asText();
        Team team = new Team();
        team.setExternalId(extId);
        team.setName(name);
        return team;
    }

    public String getScores() throws Exception{
        HttpEntity<String> request = setupRequest2();
        ResponseEntity<String> response = this.restTemplate.exchange("https://ca.global.nba.com/stats2/scores/daily.json?countryCode=CA&gameDate=2020-10-09&locale=en&tz=-5", HttpMethod.GET, request, String.class);
        System.out.println(response);
        JsonNode responseJson = this.objectMapper.readTree(response.getBody());
        return parseScore(responseJson);
    }

//    public Map<String, List<BaseModel>> getResults() throws Exception {
////            response = this.restTemplate.exchange("https://stats.nba.com/stats/commonteamroster?Season=" + RestClient.SEASON + "&TeamID=" + teamId, HttpMethod.GET, request, String.class);
////            response = this.restTemplate.exchange("https://stats.nba.com/leaguegamefinder?PlayerOrTeam=T&TeamId=" + , HttpMethod.GET, request, String.class);
//        Map<String, List<BaseModel>> results = new HashMap<>();
//        List<BaseModel> teamList = new ArrayList<>();
//        List<BaseModel> playerList = new ArrayList<>();
//        List<BaseModel> rosterList = new ArrayList<>();
//
//        for (String teamName : NbaRestClient.TEAM_MAP.keySet()) {
//            Team team = populateTeam(teamName);
//            List<Player> players = populatePlayers(teamName);
//            List<Roster> rosters = populateRosters(team, players);
//            teamList.add(team);
////            playerList.addAll(players);
//            rosterList.addAll(rosters);
//        }
//        results.put("teams", teamList);
//        results.put("players", playerList);
//        results.put("rosters", rosterList);
//
//        return results;
//    }

//    public Team populateTeam(String teamName) throws Exception{
//        HttpEntity<String> request = setupRequest();
//        Map<String, String> urlParameters = Map.of("TeamId", TEAM_MAP.get(teamName));
//        ResponseEntity<String> response = this.restTemplate.exchange("https://stats.nba.com/stats/teamdetails?TeamId={TeamId}", HttpMethod.GET, request, String.class, urlParameters);
//        JsonNode responseJson = this.objectMapper.readTree(response.getBody());
//        Team team = parseTeam(responseJson);
//        teamService.insertTeam(team);
//        return team;
//    }
//
//    public List<Player> populatePlayers(String teamName) throws Exception{
//        HttpEntity<String> request = setupRequest();
//        Map<String, String> urlParameters = Map.of("TeamId", TEAM_MAP.get(teamName), "Season", SEASON);
//        ResponseEntity<String> response = this.restTemplate.exchange("https://stats.nba.com/stats/commonteamroster?TeamId={TeamId}&Season={Season}", HttpMethod.GET, request, String.class, urlParameters);
//        JsonNode responseJson = this.objectMapper.readTree(response.getBody());
//        List<Player> players = parsePlayers(responseJson);
//        for (Player player : players) {
//            playerService.insertPlayer(player);
//        }
//        return players;
//    }
//
//    public List<Roster> populateRosters(Team team, List<Player> players) {
//        List<Roster> rosters = new ArrayList<>();
//        for (Player player : players){
//            Roster roster = new Roster();
//            roster.setPlayer(player);
//            roster.setTeam(team);
//            rosterService.insertRoster(roster);
//            rosters.add(roster);
//        }
//        return rosters;
//    }

    public List<Game> populateGames(String teamName, Team team) throws Exception{
        HttpEntity<String> request = setupRequest();
//        Map<String, String> urlParameters = Map.of("PlayerOrTeam", "T", "TeamId", TEAM_MAP.get(teamName), "DateFrom", "09/20/2020", "DateTo", "10/10/2020");
//        ResponseEntity<String> response = this.restTemplate.exchange("https://stats.nba.com/leaguegamefinder?PlayerOrTeam={PlayerOrTeam}&TeamId={TeamId}&DateFrom={DateFrom}&DateTo={DateTo}", HttpMethod.GET, request, String.class, urlParameters);
        ResponseEntity<String> response = this.restTemplate.exchange("https://stats.nba.com/stats/leaguegamefinder?PlayerOrTeam=T&TeamID=" + TEAM_MAP.get(teamName) + "&DateFrom=09/25/2020&DateTo=10/10/2020", HttpMethod.GET, request, String.class);
        JsonNode responseJson = this.objectMapper.readTree(response.getBody());
        return parseGames(responseJson);
    }

    private List<Player> parsePlayers(JsonNode responseJson){
        JsonNode playerListJson = responseJson
                .get("resultSets")
                .get(0)
                .get("rowSet");
        List<Player> players = new ArrayList<>();
        for (JsonNode playerJson : playerListJson){
            String name = playerJson.get(3).textValue();
            Player player = new Player();
            player.setName(name);
            players.add(player);
        }
        return players;
    }

    private List<Game> parseGames(JsonNode responseJson){
        JsonNode gameList = responseJson.get("resultSets").get(0).get("rowSet");
        List<Game> games = new ArrayList<>();
        for (JsonNode gameJson : gameList){
            Game game = new Game();//gameJson.get(3).textValue()
            games.add(game);
        }
        return games;
    }

    private String parseScore(JsonNode responseJson){
        JsonNode gameList = responseJson.get("payload").get("date").get("games");
        JsonNode game = gameList.get(0);
        String homeTeam = game.get("homeTeam").get("profile").get("abbr").textValue();
        String awayTeam = game.get("awayTeam").get("profile").get("abbr").textValue();
        String homeScore = game.get("homeTeam").get("score").get("score").textValue();
        String awayScore = game.get("awayTeam").get("score").get("score").textValue();
        return homeTeam + " vs " + awayTeam + ": " + homeScore + "-" + awayScore;
    }

    private HttpEntity<String> setupRequest(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Host","stats.nba.com");
        headers.set("x-nba-stats-origin","stats");
        headers.set("x-nba-stats-token","true");
        headers.set("Referer","https://stats.nba.com");
        headers.set("Accept","application/json");
        headers.set("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:72.0) Gecko/20100101 Firefox/72.0");
        return new HttpEntity<>(headers);
    }

    private HttpEntity<String> setupRequest2(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Host","ca.global.nba.com");
        headers.set("Referer","https://ca.global.nba.com/scores");
        headers.set("Accept","application/json");
        headers.set("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:72.0) Gecko/20100101 Firefox/72.0");
        return new HttpEntity<>(headers);
    }

}
