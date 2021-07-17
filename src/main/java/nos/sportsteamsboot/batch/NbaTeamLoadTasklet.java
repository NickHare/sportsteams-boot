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

public class NbaTeamLoadTasklet implements Tasklet {

    private static final Logger logger = LoggerFactory.getLogger(NbaTeamLoadTasklet.class);

    @Autowired private PlayerService playerService;
    @Autowired private TeamService teamService;
    @Autowired private RosterService rosterService;
    @Autowired private NbaRestClient restClient;

    public NbaTeamLoadTasklet(){
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

    private List<List<Roster>> processTeamPlayers(Team team) {
        List<List<Roster>> results;
        List<Player> activePlayers;
        List<Roster> existingActiveRosters;
        List<Roster> activeRosters = new ArrayList<>();

        Roster activeRoster, existingActiveRoster;
        Player activePlayer, existingActivePlayer;
        String activeExternalId, existingActiveExternalId;

        activePlayers = this.restClient.fetchTeamPlayers(Long.valueOf(team.getExternalId()));
        activePlayers.sort(playerComparator);
        logger.info("Fetched NBA Players for Team: " + team + ". Players: " + activePlayers + ".");

        existingActiveRosters = this.rosterService.getActiveRostersByTeam(team);
        existingActiveRosters.sort(rosterComparator);
        existingActiveRosters.stream().filter((Roster r) -> (r == null || r.getPlayer() == null ||))
        logger.info("Retrieved Active NBA Rosters for Team: " + team + ". Rosters: " + existingActiveRosters + ".");

        int activePlayerIndex = 0;
        int existingActiveRosterIndex = 0;

        while (activePlayerIndex < activePlayers.size() && activePlayers.get(activePlayerIndex) == null){
            activePlayerIndex++;
            logger.warn("Player from NBA API is null in some manner. Skipped Player: " + activePlayers.get + ".");
        }
        while (existingActiveRosterIndex < existingActiveRosters.size() &&
            (existingActiveRoster  == null ||
                existingActiveRosters.get(existingActiveRosterIndex).getPlayer() == null ||
                existingActiveRosters.get(existingActiveRosterIndex).getTeam() == null
            )
        ){

        }
        while (activePlayerIndex < activePlayers.size() || existingActiveRosterIndex < existingActiveRosters.size()){

        }

        Iterator<Player> activePlayersIterator = activePlayers.iterator();
        Iterator<Roster> existingActiveRostersIterator = existingActiveRosters.iterator();

        while (activePlayersIterator.hasNext() || existingActiveRostersIterator.hasNext()){
            if (!activePlayersIterator.hasNext()){

            }
            activePlayer = activePlayersIterator.hasNext()? activePlayersIterator.next() : null;
            activeExternalId = (activePlayer != null)? activePlayer.getExternalId() : null;

            existingActiveRoster = existingActiveRostersIterator.hasNext()? existingActiveRostersIterator.next(): null;
            existingActivePlayer = (existingActiveRoster != null)? existingActiveRoster.getPlayer() : null;
            existingActiveExternalId = (existingActivePlayer != null)? existingActivePlayer.getExternalId() : null;
            existingActiveRostersIterator.
        }

        while (activePlayersIterator.hasNext() &&
                ((activePlayer = activePlayersIterator.next()) == null)
        ){
            activePlayer = activePlayersIterator.next();
            activePlayersIterator.remove();
            logger.warn("Player from NBA API is null in some manner. Skipped Player: " + activePlayer + ".");
        }

        while (existingActiveRostersIterator.hasNext() &&
                ((existingActiveRoster = existingActiveRostersIterator.next()) == null || existingActiveRoster.getPlayer() == null)
        ){
            if (existingActiveRoster != null) {
                existingActiveRoster.setActive(false);
                this.rosterService.insertRoster(existingActiveRoster);
            }
            existingActiveRostersIterator.remove();
            logger.warn("Roster from RosterService is null in some manner. Deactivated roster: " + existingActiveRoster + ".");
        }

        while (activePlayersIterator.hasNext() && existingActiveRostersIterator.hasNext()) {
            activePlayer = activePlayersIterator.next();
            existingActiveRoster = existingActiveRostersIterator.next();

            if (activePlayer == null || activePlayer.getExternalId() == null)

            activeExternalId = activePlayer.getExternalId();
            existingActivePlayer = existingActiveRoster.getPlayer();
            existingActiveExternalId = existingActivePlayer.getExternalId();

            if (existingActiveExternalId.compareTo(activeExternalId) == 0){
                activeRosters.add(existingActiveRoster);
                logger.info("Player with ExternalId: " + existingActiveExternalId + " already active on Team with ExternalId " + team.getExternalId() + ". Skipped Roster: " + existingActiveRoster + ".");
            } else if (activeExternalId.compareTo(existingActiveExternalId) > 0){
                existingActiveRoster.setActive(false);
                this.rosterService.insertRoster(existingActiveRoster);
                logger.info("Player with ExternalId: " + existingActiveExternalId + " is no longer on the official roster for the Team with ExternalId: " + team.getExternalId() + ". Deactivated Roster: " + existingActiveRoster + ".");
            } else if (activeExternalId.compareTo(existingActiveExternalId) < 0){
                activeRoster = new Roster(null, team.getExternalId() + "-" + existingActivePlayer.getExternalId(), existingActivePlayer, team, true, null, null);
                this.rosterService.insertRoster(activeRoster);
                activeRosters.add(activeRoster);
                logger.info("Player with ExternalId " + activeExternalId + " is now on the official roster for the Team with ExternalId " + team.getExternalId() + ". Inserted Roster: " + activeRoster + ".");
            }
        }

        while (activePlayersIterator.hasNext() || existingActiveRostersIterator.hasNext()) {
            activePlayer = activePlayersIterator.hasNext()? activePlayersIterator.next() : null;
            activeExternalId = (activePlayer != null)? activePlayer.getExternalId() : null;

            existingActiveRoster = existingActiveRostersIterator.hasNext()? existingActiveRostersIterator.next(): null;
            existingActivePlayer = (existingActiveRoster != null)? existingActiveRoster.getPlayer() : null;
            existingActiveExternalId = (existingActivePlayer != null)? existingActivePlayer.getExternalId() : null;

            if (activePlayer == null && existingActiveRoster != null){
                existingActiveRoster.setActive(false);
                this.rosterService.insertRoster(existingActiveRoster);
                logger.info("Player with ExternalId: " + existingActiveExternalId + " is no longer on the official roster for the Team with ExternalId: " + team.getExternalId() + ". Deactivated Roster: " + existingActiveRoster + ".");
            } else if (existingActiveRoster == null && activePlayer != null){
                this.playerService.insertPlayer(activePlayer);
                activeRoster = new Roster(null, team.getExternalId() + "-" + activePlayer.getExternalId(), activePlayer, team, true, null, null);
                this.rosterService.insertRoster(activeRoster);
                activeRosters.add(activeRoster);
                logger.info("Player with ExternalId " + activeExternalId + " has been created and is now on the official roster for the Team with ExternalId " + team.getExternalId() + ". Inserted Roster: " + activeRoster + ".");
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
}
