package nos.sportsteamsboot;

import nos.sportsteamsboot.client.NbaRestClient;
import nos.sportsteamsboot.model.Player;
import nos.sportsteamsboot.model.Roster;
import nos.sportsteamsboot.model.Team;
import nos.sportsteamsboot.repository.RosterRepository;
import nos.sportsteamsboot.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TestRunner implements CommandLineRunner {
    @Autowired private NbaRestClient nbaClient;
    @Autowired private TeamRepository teamRepository;
    @Autowired private RosterRepository rosterRepository;
    @Autowired private TransactionManager tx;
    @Autowired private ApplicationContext ctx;

    @Transactional
    public void run(String... args ){
//        List<Long> teamIds = nbaClient.fetchTeamIdList();
//        List<Team> teams = new ArrayList<>();
//        for (Long teamId : teamIds) {
////            teams.add(nbaClient.fetchTeam(teamId));
//            List<Player> players = this.nbaClient.fetchTeamPlayers(teamId);
//            System.out.println(players);
//        }
        List<Roster> r1 = this.rosterRepository.findByTeamIdAndActiveTrue(1L);
        for (Roster r : r1){
            System.out.println(r);
        }
        System.out.println("-----------------");
        List<Roster> r2 = this.rosterRepository.findByActiveTrue();
        for (Roster r : r2){
            System.out.println(r);
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
