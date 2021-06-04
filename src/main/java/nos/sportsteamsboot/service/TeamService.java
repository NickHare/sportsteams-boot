package nos.sportsteamsboot.service;

import nos.sportsteamsboot.model.Team;
import nos.sportsteamsboot.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    @Autowired TeamRepository teamRepository;

    public Team insertTeam(Team team){
        Team t = teamRepository.save(team);
        return t;
    }

    public Team getTeam(Long id){
        Team t = teamRepository.findById(id).orElse(null);
        return t;
    }

    public Team getTeamByName(String name){
        Team t = teamRepository.findByName(name).orElse(null);
        return t;
    }

    public Team getTeamByExternalId(String extId){
        Team t = teamRepository.findByName(extId).orElse(null);
        return t;
    }

    public Iterable<Team> getTeams(){
        Iterable<Team> t = teamRepository.findAll();
        return t;
    }
}
