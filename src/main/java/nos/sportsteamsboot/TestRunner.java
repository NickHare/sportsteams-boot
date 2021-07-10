package nos.sportsteamsboot;

import nos.sportsteamsboot.client.NbaRestClient;
import nos.sportsteamsboot.model.Player;
import nos.sportsteamsboot.model.Roster;
import nos.sportsteamsboot.model.Team;
import nos.sportsteamsboot.repository.PlayerRepository;
import nos.sportsteamsboot.repository.RosterRepository;
import nos.sportsteamsboot.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;


import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
//            teams.add(nbaClient.fetchTeam(teamId));
//            List<Player> players = this.nbaClient.fetchTeamPlayers(teamId);
//            System.out.println(players);
//        }
//        List<Roster> r1 = this.rosterRepository.findByTeamIdAndActiveTrue(1L);
//        for (Roster r : r1){
//            System.out.println(r);
//        }
//        System.out.println("-----------------");

//        String[] beans = Arrays.stream(ctx.getBeanDefinitionNames()).filter((String s) -> {return s.contains("ransaction");}).toArray(String[]::new);
//        String[] beans2 = Arrays.stream(ctx.getBeanDefinitionNames()).filter((String s) -> {return s.contains("ntity");}).toArray(String[]::new);
//        String[] beans3 = Arrays.stream(ctx.getBeanDefinitionNames()).filter((String s) -> {return s.contains("Jpa");}).toArray(String[]::new);
//        Object tx = ctx.getBean("transactionManager");
//        Object emf = ctx.getBean("entityManagerFactory");
//        Object ds = ctx.getBean("dataSource");
//        Object jr = ctx.getBean("jobRepository");
//        Object tr = ctx.getBean("teamRepository");

//        List<Player> players = new ArrayList<>(Arrays.asList(
//                new Player(1L, "1", "Alex", null, null),
//                new Player(2L, "2", "Dan", null, null),
//                new Player(3L, "3", "Nick", null, null),
//                new Player(4L, null, "Aaron", null, null),
//                new Player(5L, null, "Rory", null, null),
//                null,
//                null
//        ));
//        Team team = new Team(1L, "1", "Easy Company", "EC", null, null);
//        List<Roster> rosters = new ArrayList<>(Arrays.asList(
//                new Roster(1L, "", players.get(0), team, true, null, null),
//                new Roster(2L, "", players.get(1), team, true, null, null),
//                new Roster(3L, "", players.get(2), team, true, null, null),
//                new Roster(4L, "", players.get(3), team, true, null, null),
//                new Roster(5L, "", null, team, true, null, null),
//                null
//        ));
//        Comparator<Player> c1 = Comparator.nullsFirst(Comparator.comparing(Player::getExternalId)).reversed();
//        Comparator<Player> c1 = Comparator.nullsFirst(
//                Comparator.comparing(Player::getExternalId, Comparator.nullsFirst(
//                        Comparator.naturalOrder()
//                ))
//        );
//        Comparator<Roster> c2 = Comparator.nullsFirst(
//                Comparator.comparing(Roster::getPlayer, Comparator.nullsFirst(
//                        Comparator.comparing(Player::getExternalId, Comparator.nullsFirst(
//                                Comparator.naturalOrder()
//                        ))
//                ))
//        );
//        c2.compare(rosters.get(0), rosters.get(1));
//        c2.compare(rosters.get(0), rosters.get(4));
//        c2.compare(rosters.get(0), rosters.get(5));
//        c2.compare(null, rosters.get(0));
//        c2.compare(null, rosters.get(4));
//        c2.compare(null, rosters.get(5));
//        c2.compare(null, null);
//        players.sort(c1);
//        rosters.sort(c2);

        System.out.println("Done");
    }
}
