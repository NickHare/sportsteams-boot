package nos.sportsteamsboot.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nos.sportsteamsboot.model.*;
import nos.sportsteamsboot.service.PlayerService;
import nos.sportsteamsboot.service.RosterService;
import nos.sportsteamsboot.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NbaRestClient {
    private static final Map<String, String> TEAM_MAP = Map.ofEntries(
//            Map.entry("Raptors", "1610612761"),
            Map.entry("Lakers", "1610612747"),
            Map.entry("Heat", "1610612748")
    );
    private static final String SEASON = "2019-20";

    private ObjectMapper objectMapper;
    private RestTemplate restTemplate;

    @Autowired private RosterService rosterService;
    @Autowired private PlayerService playerService;
    @Autowired private TeamService teamService;

    public NbaRestClient(RestTemplateBuilder builder){
        this.objectMapper = new ObjectMapper();
        this.restTemplate = builder.build();
    }

    public String getScores() throws Exception{
        HttpEntity<String> request = setupRequest2();
        ResponseEntity<String> response = this.restTemplate.exchange("https://ca.global.nba.com/stats2/scores/daily.json?countryCode=CA&gameDate=2020-10-09&locale=en&tz=-5", HttpMethod.GET, request, String.class);
        System.out.println(response);
        JsonNode responseJson = this.objectMapper.readTree(response.getBody());
        return parseScore(responseJson);
    }

    public Map<String, List<BaseModel>> getResults() throws Exception {
//            response = this.restTemplate.exchange("https://stats.nba.com/stats/commonteamroster?Season=" + RestClient.SEASON + "&TeamID=" + teamId, HttpMethod.GET, request, String.class);
//            response = this.restTemplate.exchange("https://stats.nba.com/leaguegamefinder?PlayerOrTeam=T&TeamId=" + , HttpMethod.GET, request, String.class);
        Map<String, List<BaseModel>> results = new HashMap<>();
        List<BaseModel> teamList = new ArrayList<>();
        List<BaseModel> playerList = new ArrayList<>();
        List<BaseModel> rosterList = new ArrayList<>();

        for (String teamName : NbaRestClient.TEAM_MAP.keySet()) {
            Team team = populateTeam(teamName);
            List<Player> players = populatePlayers(teamName);
            List<Roster> rosters = populateRosters(team, players);
            teamList.add(team);
//            playerList.addAll(players);
            rosterList.addAll(rosters);
        }
        results.put("teams", teamList);
        results.put("players", playerList);
        results.put("rosters", rosterList);

        return results;
    }

    public Team populateTeam(String teamName) throws Exception{
        HttpEntity<String> request = setupRequest();
        Map<String, String> urlParameters = Map.of("TeamId", TEAM_MAP.get(teamName));
        ResponseEntity<String> response = this.restTemplate.exchange("https://stats.nba.com/stats/teamdetails?TeamId={TeamId}", HttpMethod.GET, request, String.class, urlParameters);
        JsonNode responseJson = this.objectMapper.readTree(response.getBody());
        Team team = parseTeam(responseJson);
        teamService.insertTeam(team);
        return team;
    }

    public List<Player> populatePlayers(String teamName) throws Exception{
        HttpEntity<String> request = setupRequest();
        Map<String, String> urlParameters = Map.of("TeamId", TEAM_MAP.get(teamName), "Season", SEASON);
        ResponseEntity<String> response = this.restTemplate.exchange("https://stats.nba.com/stats/commonteamroster?TeamId={TeamId}&Season={Season}", HttpMethod.GET, request, String.class, urlParameters);
        JsonNode responseJson = this.objectMapper.readTree(response.getBody());
        List<Player> players = parsePlayers(responseJson);
        for (Player player : players) {
            playerService.insertPlayer(player);
        }
        return players;
    }

    public List<Roster> populateRosters(Team team, List<Player> players) {
        List<Roster> rosters = new ArrayList<>();
        for (Player player : players){
            Roster roster = new Roster();
            roster.setPlayer(player.getId());
            roster.setTeam(team.getId());
            rosterService.insertRoster(roster);
            rosters.add(roster);
        }
        return rosters;
    }

    public List<Game> populateGames(String teamName, Team team) throws Exception{
        HttpEntity<String> request = setupRequest();
//        Map<String, String> urlParameters = Map.of("PlayerOrTeam", "T", "TeamId", TEAM_MAP.get(teamName), "DateFrom", "09/20/2020", "DateTo", "10/10/2020");
//        ResponseEntity<String> response = this.restTemplate.exchange("https://stats.nba.com/leaguegamefinder?PlayerOrTeam={PlayerOrTeam}&TeamId={TeamId}&DateFrom={DateFrom}&DateTo={DateTo}", HttpMethod.GET, request, String.class, urlParameters);
        ResponseEntity<String> response = this.restTemplate.exchange("https://stats.nba.com/stats/leaguegamefinder?PlayerOrTeam=T&TeamID=" + TEAM_MAP.get(teamName) + "&DateFrom=09/25/2020&DateTo=10/10/2020", HttpMethod.GET, request, String.class);
        JsonNode responseJson = this.objectMapper.readTree(response.getBody());
        return parseGames(responseJson);
    }

    private Team parseTeam(JsonNode responseJson){
        JsonNode teamJson = responseJson.get("resultSets").get(0).get("rowSet").get(0);
        String name = teamJson.get(2).asText();
        Team team = new Team();
        team.setName(name);
        return team;
    }

    private List<Player> parsePlayers(JsonNode responseJson){
        JsonNode playerList = responseJson.get("resultSets").get(0).get("rowSet");
        List<Player> players = new ArrayList<>();
        for (JsonNode playerJson : playerList){
            String name = playerJson.get(3).asText();
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
            Game game = new Game();//gameJson.get(3).asText()
            games.add(game);
        }
        return games;
    }

    private String parseScore(JsonNode responseJson){
        JsonNode gameList = responseJson.get("payload").get("date").get("games");
        JsonNode game = gameList.get(0);
        String homeTeam = game.get("homeTeam").get("profile").get("abbr").asText();
        String awayTeam = game.get("awayTeam").get("profile").get("abbr").asText();
        String homeScore = game.get("homeTeam").get("score").get("score").asText();
        String awayScore = game.get("awayTeam").get("score").get("score").asText();
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
