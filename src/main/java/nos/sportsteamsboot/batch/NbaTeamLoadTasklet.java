package nos.sportsteamsboot.batch;

import nos.sportsteamsboot.client.NbaRestClient;
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

public class NbaTeamLoadTasklet implements Tasklet {

    private static final Logger logger = LoggerFactory.getLogger(NbaTeamLoadTasklet.class);
    private List<Long> teamIds;

    @Autowired private TeamService teamService;
    @Autowired private NbaRestClient restClient;

    public NbaTeamLoadTasklet(){
    }

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        if (this.teamIds == null){
            logger.info("Fetching NBA TeamIds");
            this.teamIds = this.restClient.fetchTeamIdList();
            logger.info("Fetched NBA TeamIds: " + this.teamIds);
            Thread.sleep(500);
        }

        for (Long teamId : this.teamIds){
            Team existingTeam = this.teamService.getTeamByExternalId(teamId.toString());
            if (existingTeam == null){
                logger.info("Fetching NBA Team with ExternalId: " + teamId);
                Team fetchedTeam = this.restClient.fetchTeam(teamId);
                logger.info("Fetched NBA Team: " + fetchedTeam);
                Team insertedTeam = this.teamService.insertTeam(fetchedTeam);
                logger.info("Fetched NBA Team: " + insertedTeam);
            } else{
                logger.info("Team already exists with that ExternalId. Skipped existing Team: " + existingTeam);
            }
            Thread.sleep(500);
        }
        return RepeatStatus.FINISHED;
    }
}
