package nos.sportsteamsboot;

import nos.sportsteamsboot.client.NbaRestClient;
import nos.sportsteamsboot.model.Player;
import nos.sportsteamsboot.model.Team;
import nos.sportsteamsboot.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TestRunner implements CommandLineRunner {
    @Autowired private NbaRestClient nbaClient;
    @Autowired private TeamRepository teamRepository;
    @Autowired private TransactionManager tx;
    @Autowired private ApplicationContext ctx;

    public void run(String... args ){
        List<Long> teamIds = nbaClient.fetchTeamIdList();
        List<Team> teams = new ArrayList<>();
        for (Long teamId : teamIds) {
//            teams.add(nbaClient.fetchTeam(teamId));
            List<Player> players = this.nbaClient.fetchTeamPlayers(teamId);
            System.out.println(players);
        }

//        String[] beans = Arrays.stream(ctx.getBeanDefinitionNames()).filter((String s) -> {return s.contains("ransaction");}).toArray(String[]::new);
//        String[] beans2 = Arrays.stream(ctx.getBeanDefinitionNames()).filter((String s) -> {return s.contains("ntity");}).toArray(String[]::new);
//        String[] beans3 = Arrays.stream(ctx.getBeanDefinitionNames()).filter((String s) -> {return s.contains("Jpa");}).toArray(String[]::new);
//        Object tx = ctx.getBean("transactionManager");
//        Object emf = ctx.getBean("entityManagerFactory");
//        Object ds = ctx.getBean("dataSource");
//        Object jr = ctx.getBean("jobRepository");
//        Object tr = ctx.getBean("teamRepository");
        return;
    }
}
