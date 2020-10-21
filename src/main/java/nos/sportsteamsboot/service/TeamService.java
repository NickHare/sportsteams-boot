package nos.sportsteamsboot.service;

import nos.sportsteamsboot.model.Player;
import nos.sportsteamsboot.model.Team;
import nos.sportsteamsboot.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    @Autowired TeamRepository teamRepository;

    public Team insertTeam(Team team){
        Team t = null;
        try {
            t = teamRepository.save(team);
        } catch(Exception e){
            e.printStackTrace();
        }
        return t;
    }

    public Team getTeam(Long id){
        Team t = null;
        try {
            t = teamRepository.findById(id).orElse(Team.EmptyTeam);
        } catch(Exception e){
            e.printStackTrace();
        }
        return t;
    }

    public Team getTeamByName(String name){
        Team t = null;
        try {
            t = teamRepository.findByName(name);
        } catch(Exception e){
            e.printStackTrace();
        }
        return t;
    }

    public Iterable<Team> getTeams(){
        Iterable<Team> t = null;
        try {
            t = teamRepository.findAll();
        } catch(Exception e){
            e.printStackTrace();
        }
        return t;
    }
}
