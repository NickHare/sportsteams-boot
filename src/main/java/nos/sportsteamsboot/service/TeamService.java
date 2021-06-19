package nos.sportsteamsboot.service;

import nos.sportsteamsboot.model.Team;
import nos.sportsteamsboot.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {
    @Autowired TeamRepository teamRepository;

    public Team insertTeam(Team team){
        Team t = teamRepository.save(team);
        return t;
    }

    public Optional<Team> getTeam(Long id){
        return teamRepository.findById(id);
    }

    public Optional<Team> getTeamByName(String name){
        return teamRepository.findByName(name);
    }

    public Optional<Team> getTeamByExternalId(String extId){
        return teamRepository.findByName(extId);
    }

    public Iterable<Team> getTeams(){
        return teamRepository.findAll();
    }
}
