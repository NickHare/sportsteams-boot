//package nos.sportsteamsboot.batch;
//
//import nos.sportsteamsboot.model.Team;
//import nos.sportsteamsboot.repository.TeamRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//public class NbaTeamItemWriter implements ItemWriter<Team> {
//    private static final Logger logger = LoggerFactory.getLogger(NbaTeamItemWriter.class);
//
//    private TeamRepository teamRepository;
//
//    public NbaTeamItemWriter(TeamRepository teamRepository){
//        this.teamRepository = teamRepository;
//    }
//
//    public void write(List<? extends Team> teamList){
//        for (Team team : teamList) {
//            team = teamRepository.save(team);
//            logger.info("Team saved: " + team.toString());
//        }
//        try {
//            Thread.sleep(5000);
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//}
