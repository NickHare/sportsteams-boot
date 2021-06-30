package nos.sportsteamsboot.batch;

import nos.sportsteamsboot.client.NbaRestClient;
import nos.sportsteamsboot.model.Player;
import nos.sportsteamsboot.model.Team;
import nos.sportsteamsboot.repository.TeamRepository;
import nos.sportsteamsboot.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class NbaTeamLoadTasklet implements Tasklet {

    private static final Logger logger = LoggerFactory.getLogger(NbaTeamLoadTasklet.class);

    @Autowired private TeamService teamService;
    @Autowired private NbaRestClient restClient;

    public NbaTeamLoadTasklet(){
    }

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        //Get all TeamIds from NBA API
        logger.info("Fetching NBA TeamIds.");
        List<Long> teamIds = this.restClient.fetchTeamIdList();
        logger.info("Fetched NBA TeamIds: " + teamIds + ".");

        for (Long teamId : teamIds){
            Optional<Team> team = processTeam(teamId);
            if (team.isPresent()) {
//                List<Player> players = processPlayers(teamId);
            }
        }
        return RepeatStatus.FINISHED;
    }

    private Optional<Team> processTeam(Long teamId) throws Exception{
        Optional<Team> existingTeam = this.teamService.getTeam(teamId);
        Optional<Team> processedTeam = Optional.empty();
        if (existingTeam.isPresent()){
            //Team exists in system, skip team
            logger.info("Team with with that ExternalId " + teamId + " already exists. Skipped existing Team: " + existingTeam + ".");
            processedTeam = existingTeam;
        } else {
            //Team does not exist in system, fetch and insert Team
            Thread.sleep(500);
            logger.info("Fetching NBA Team with ExternalId: " + teamId + ".");
            Optional<Team> fetchedTeam = this.restClient.fetchTeam(teamId);
            if (fetchedTeam.isEmpty()){
                logger.info("Team with with that ExternalId " + teamId + " could not be fetched. Skipped Team.");
                processedTeam = Optional.empty();
            } else {
                logger.info("Fetched NBA Team: " + fetchedTeam + ".");
                processedTeam = Optional.of(this.teamService.insertTeam(fetchedTeam.get()));
                logger.info("Inserted NBA Team: " + processedTeam + ".");
            }
        }
        return processedTeam;
    }

//    private List<Player> processPlayers(Long teamId){
//        List<Player> activePlayers = this.teamService.get
//    }
}
