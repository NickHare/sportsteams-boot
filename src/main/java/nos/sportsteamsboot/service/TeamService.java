package nos.sportsteamsboot.service;

import nos.sportsteamsboot.model.Team;
import nos.sportsteamsboot.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired TeamRepository teamRepository;

    public Team insertTeam(Team team){
        return this.teamRepository.save(team);
    }

    public Optional<Team> getTeam(Long id){
        return this.teamRepository.findById(id);
    }

    public Optional<Team> getTeamByName(String name){
        return this.teamRepository.findByName(name);
    }

    public Optional<Team> getTeamByExternalId(String extId){
        return this.teamRepository.findByExternalId(extId);
    }

    public List<Team> getTeams(){
        return this.teamRepository.findAll();
    }
}
