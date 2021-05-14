package nos.sportsteamsboot;

import nos.sportsteamsboot.client.NbaRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class TestRunner implements CommandLineRunner {
    @Autowired private NbaRestClient nbaClient;

    public void run(String... args ){
        Long[] teamIds = nbaClient.fetchTeamIdList().toArray(new Long[0]);
    }
}
