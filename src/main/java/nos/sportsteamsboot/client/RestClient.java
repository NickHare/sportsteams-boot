package nos.sportsteamsboot.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestClient {
    private static final String[] TEAMS = {"1610612761"};

    private final RestTemplate restTemplate;

    public RestClient(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    public void getTeams(){
        for (String team : RestClient.TEAMS) {
//            this.restTemplate.getForEntity("https://stats.nba.com/stats/");
        }
    }
}
