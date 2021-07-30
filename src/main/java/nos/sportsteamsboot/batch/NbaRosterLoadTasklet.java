package nos.sportsteamsboot.batch;

import nos.sportsteamsboot.client.NbaRestClient;
import nos.sportsteamsboot.model.Player;
import nos.sportsteamsboot.model.Roster;
import nos.sportsteamsboot.model.Team;
import nos.sportsteamsboot.service.PlayerService;
import nos.sportsteamsboot.service.RosterService;
import nos.sportsteamsboot.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class NbaRosterLoadTasklet implements Tasklet {

    private static final Logger logger = LoggerFactory.getLogger(NbaRosterLoadTasklet.class);

    @Autowired private PlayerService playerService;
    @Autowired private TeamService teamService;
    @Autowired private RosterService rosterService;
    @Autowired private NbaRestClient restClient;

    public NbaRosterLoadTasklet(){
    }

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        //Get all TeamIds from NBA API
        List<Long> teamIds = this.restClient.fetchTeamIdList();
        logger.info("Fetched NBA TeamIds: " + teamIds + ".");

        for (Long teamId : teamIds){
            Optional<Team> team = processTeam(teamId);
            if (team.isPresent()) {
                List<Roster> activeRoster = processTeamPlayers(team.get());
            }
        }
        return RepeatStatus.FINISHED;
    }

    private Optional<Team> processTeam(Long teamId){
        Optional<Team> existingTeam;
        Optional<Team> fetchedTeam;
        Optional<Team> processedTeam = Optional.empty();

        existingTeam = this.teamService.getTeamByExternalId(String.valueOf(teamId));
        if (existingTeam.isPresent()){
            //Team exists in system, skip team
            logger.info("Team with with that ExternalId " + teamId + " already exists. Skipped existing Team: " + existingTeam.get() + ".");
            processedTeam = existingTeam;
        } else {
            //Team does not exist in system, fetch and insert Team
            fetchedTeam = this.restClient.fetchTeam(teamId);
            if (fetchedTeam.isEmpty()){
                processedTeam = Optional.empty();
                logger.info("Team with with that ExternalId " + teamId + " could not be fetched. Skipped Team.");
            } else {
                processedTeam = Optional.of(this.teamService.insertTeam(fetchedTeam.get()));
                logger.info("Fetched and inserted NBA Team with ExternalId: " + teamId + ". Team: " + processedTeam.get() + ".");
            }
        }
        return processedTeam;
    }

    private List<Roster> processTeamPlayers(Team team) {
        //Initialize Variables for Iterating and Results
        List<Player> activePlayers;
        List<Roster> existingActiveRosters;
        List<Roster> activeRosters = new ArrayList<>();

        Roster activeRoster, existingActiveRoster;
        Player activePlayer, existingActivePlayer;
        String activeExternalId, existingActiveExternalId;

        int activePlayerIndex = 0;
        int existingActiveRosterIndex = 0;

        //Fetch NBA Active Players from the NBA Rest client
        activePlayers = this.restClient.fetchTeamPlayers(Long.valueOf(team.getExternalId()));
        activePlayers.sort(playerComparator);
        logger.info("Fetched NBA Players for Team: " + team + ". Players: " + activePlayers + ".");

        //Retrieve NBA Active Rosters from Storage
        existingActiveRosters = this.rosterService.getActiveRostersByTeam(team);
        existingActiveRosters.sort(rosterComparator);
        logger.info("Retrieved Active NBA Rosters for Team: " + team + ". Rosters: " + existingActiveRosters + ".");

        //Pass over all null-ish active Players that were fetched from NBA
        while (activePlayerIndex < activePlayers.size() && isPlayerNullish(activePlayers.get(activePlayerIndex))){
            activePlayer = activePlayers.get(activePlayerIndex);
            activePlayerIndex++;
            logger.warn("Player from NBA API is null in some manner. Skipped Player: " + activePlayer + ".");
        }

        //Remove any null-ish active Rosters that were retrieved from Storage
        while (existingActiveRosterIndex < existingActiveRosters.size() && isRosterNullish(existingActiveRosters.get(existingActiveRosterIndex))){
            existingActiveRoster = existingActiveRosters.get(existingActiveRosterIndex);
            if (existingActiveRoster != null) {
                existingActiveRoster.setActive(false);
                this.rosterService.insertRoster(existingActiveRoster);
            }
            logger.warn("Roster from RosterService is null in some manner. Deactivated roster: " + existingActiveRoster + ".");
        }

        //Process any remaining active Players fetched/retrieved from NBA/Storage
        while (activePlayerIndex < activePlayers.size() || existingActiveRosterIndex < existingActiveRosters.size()){
            activePlayer = (activePlayerIndex < activePlayers.size())? activePlayers.get(activePlayerIndex) : null;
            activeExternalId = (activePlayer != null)? activePlayer.getExternalId() : null;

            existingActiveRoster = (existingActiveRosterIndex < existingActiveRosters.size())? existingActiveRosters.get(existingActiveRosterIndex) : null;
            existingActivePlayer = (existingActiveRoster != null)? existingActiveRoster.getPlayer() : null;
            existingActiveExternalId = (existingActivePlayer != null)? existingActivePlayer.getExternalId() : null;

            //Deactivate Rosters that NBA shows as no longer active from Storage
            if ((activePlayer == null && existingActiveRoster != null) ||
                ( existingActiveRoster != null && activeExternalId.compareTo(existingActiveExternalId) > 0)
            ){
                existingActiveRoster.setActive(false);
                this.rosterService.insertRoster(existingActiveRoster);
                existingActiveRosterIndex++;
                logger.info("Player with ExternalId: " + existingActiveExternalId + " is no longer on the official roster for the Team with ExternalId: " + team.getExternalId() + ". Deactivated Roster: " + existingActiveRoster + ".");
            }
            //Insert Rosters that NBA show as newly active into Storage
            else if ((existingActiveRoster == null && activePlayer != null) || activeExternalId.compareTo(existingActiveExternalId) < 0){
                this.playerService.insertPlayer(activePlayer);
                activeRoster = new Roster(null, team.getExternalId() + "-" + activePlayer.getExternalId(), activePlayer, team, true, null, null);
                this.rosterService.insertRoster(activeRoster);
                activeRosters.add(activeRoster);
                activePlayerIndex++;
                logger.info("Player with ExternalId " + activeExternalId + " has been created and is now on the official roster for the Team with ExternalId " + team.getExternalId() + ". Inserted Roster: " + activeRoster + ".");
            }
            //Skip Rosters that are already currently active
            else if (activeExternalId.compareTo(existingActiveExternalId) == 0){
                activeRosters.add(existingActiveRoster);
                activePlayerIndex++;
                existingActiveRosterIndex++;
                logger.info("Player with ExternalId: " + existingActiveExternalId + " already active on Team with ExternalId " + team.getExternalId() + ". Skipped Roster: " + existingActiveRoster + ".");
            }
        }
        return activeRosters;
    }

    private static final Comparator<Player> playerComparator = Comparator.nullsFirst(
            Comparator.comparing(Player::getExternalId, Comparator.nullsFirst(
                    Comparator.naturalOrder()
            ))
    );

    private static final Comparator<Roster> rosterComparator = Comparator.nullsFirst(
            Comparator.comparing(Roster::getPlayer, Comparator.nullsFirst(
                    Comparator.comparing(Player::getExternalId, Comparator.nullsFirst(
                            Comparator.naturalOrder()
                    ))
            ))
    );

    private static Boolean isRosterNullish(Roster roster){
        return (roster == null || roster.getTeam() == null || roster.getPlayer() == null);
    }

    private static Boolean isPlayerNullish(Player player){
        return (player == null);
    }
}
