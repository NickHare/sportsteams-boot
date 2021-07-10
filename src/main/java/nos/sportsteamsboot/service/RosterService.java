package nos.sportsteamsboot.service;

import nos.sportsteamsboot.model.Roster;
import nos.sportsteamsboot.model.Team;
import nos.sportsteamsboot.repository.RosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RosterService {
    @Autowired RosterRepository rosterRepository;

    public Roster insertRoster(Roster roster){
        return this.rosterRepository.save(roster);
    }

    public Optional<Roster> getRoster(Long id){
        return this.rosterRepository.findById(id);
    }

    public List<Roster> getRosters(){
        return this.rosterRepository.findAll();
    }

    public List<Roster> getActiveRostersByTeam(Team team){
        return this.rosterRepository.findByTeamIdAndActiveTrue(team.getId());
    }
}
