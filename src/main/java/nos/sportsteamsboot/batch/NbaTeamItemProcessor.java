package nos.sportsteamsboot.batch;

import nos.sportsteamsboot.model.Team;
import nos.sportsteamsboot.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class NbaTeamItemProcessor implements ItemProcessor<Team, Team> {
    private static final Logger logger = LoggerFactory.getLogger(NbaTeamItemProcessor.class);

    private TeamRepository teamRepository;

    public NbaTeamItemProcessor(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public Team process(Team team){
        Optional<Team> existingTeam = teamRepository.findByExternalId(team.getExternalId());
        if (!existingTeam.isEmpty()){
            logger.info("Already saved, skipped team: " + team.toString());
        }
        return existingTeam.isEmpty()? team : null;
    }
}
