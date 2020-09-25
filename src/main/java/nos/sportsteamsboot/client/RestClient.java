package nos.sportsteamsboot.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nos.sportsteamsboot.model.Player;
import nos.sportsteamsboot.model.Roster;
import nos.sportsteamsboot.model.Team;
import nos.sportsteamsboot.service.PlayerService;
import nos.sportsteamsboot.service.RosterService;
import nos.sportsteamsboot.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RestClient {
    private static final String[] TEAM_IDS = {"1610612761"};
    private static final String SEASON = "2019-20";

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @Autowired private PlayerService playerService;
    @Autowired private TeamService teamService;
    @Autowired private RosterService rosterService;

    public RestClient(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = new ObjectMapper();
    }

    public String getResults(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Host","stats.nba.com");
        headers.set("x-nba-stats-origin","stats");
        headers.set("x-nba-stats-token","true");
        headers.set("Referer","https://stats.nba.com");
        headers.set("Accept","application/json");
        headers.set("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:72.0) Gecko/20100101 Firefox/72.0");
        headers.set("Connection","keep-alive");
        HttpEntity request = new HttpEntity(headers);
        Map<Team, List<Player>> results = new HashMap<>();
        ResponseEntity<String> response = null;
        for (String teamId : RestClient.TEAM_IDS) {
            JsonNode root;
            Team team = null;
            List<Player> players = new ArrayList<>();

            response = this.restTemplate.exchange("https://stats.nba.com/stats/teamdetails?TeamID=" + teamId, HttpMethod.GET, request, String.class);
            try {
                root = this.objectMapper.readTree(response.getBody());
                String name = root.get("resultSets").get(0).get("rowSet").get(0).get(2).asText();
                team = new Team(name);
            }catch (Exception e){
                e.printStackTrace();
            }

            response = this.restTemplate.exchange("https://stats.nba.com/stats/commonteamroster?Season=" + RestClient.SEASON + "&TeamID=" + teamId, HttpMethod.GET, request, String.class);

            try {
                root = this.objectMapper.readTree(response.getBody());
                JsonNode playerList = root.get("resultSets").get(0).get("rowSet");
                System.out.println(root);
                for (JsonNode player : playerList){
                    Player p = new Player(player.get(3).asText());
                    players.add(p);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            results.put(team,players);

            response = this.restTemplate.exchange("https://stats.nba.com/leaguegamefinder?", HttpMethod.GET, request, String.class);
            System.out.println(response);
        }
        for (Team t : results.keySet()){
            teamService.insertTeam(t);
            for (Player p : results.get(t)){
                playerService.insertPlayer(p);
                Roster r = new Roster(p.getId(), t.getId());
                rosterService.insertRoster(r);
            }

        }
        return response.getBody();
    }
}
