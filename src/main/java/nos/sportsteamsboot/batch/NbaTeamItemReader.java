//package nos.sportsteamsboot.batch;
//
//
//import nos.sportsteamsboot.client.NbaRestClient;
//import nos.sportsteamsboot.model.Team;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.NonTransientResourceException;
//import org.springframework.batch.item.ParseException;
//import org.springframework.batch.item.UnexpectedInputException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//public class NbaTeamItemReader implements ItemReader<Team>{
//    private static final Logger logger = LoggerFactory.getLogger(NbaTeamItemReader.class);
//
//    private NbaRestClient restClient;
//    private List<Long> teamIdList;
//
//    public NbaTeamItemReader(NbaRestClient restClient){
//        this.restClient = restClient;
//        this.teamIdList = this.restClient.fetchTeamIdList();
//        logger.info("Fetched TeamIds: " + this.teamIdList.toString());
//    }
//
//    @Override
//    public Team read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//        if (!teamIdList.isEmpty()){
//            Long teamId = teamIdList.remove(0);
//            Team team = restClient.fetchTeam(teamId);
//            logger.info("Read Team: " + team.toString());
//            return team;
//        }
//        logger.info("Finished reading teams");
//        return null;
//    }
//}
