package nos.sportsteamsboot;

import nos.sportsteamsboot.client.NbaRestClient;
import nos.sportsteamsboot.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component
public class TestRunner implements CommandLineRunner {
    @Autowired private NbaRestClient nbaClient;

    public void run(String... args ){
        List<Long> teamIds = nbaClient.fetchTeamIdList();
        List<Team> teams = new ArrayList<>();
        for (Long teamId : teamIds) {
            teams.add(nbaClient.fetchTeam(teamId));
        }
    }
}
