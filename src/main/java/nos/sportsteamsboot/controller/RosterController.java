package nos.sportsteamsboot.controller;

import nos.sportsteamsboot.model.Player;
import nos.sportsteamsboot.model.Roster;
import nos.sportsteamsboot.model.Team;
import nos.sportsteamsboot.repository.PlayerRepository;
import nos.sportsteamsboot.repository.RosterRepository;
import nos.sportsteamsboot.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
public class RosterController {
    @Autowired RosterRepository rosterRepo;
    @Autowired PlayerRepository playerRepo;
    @Autowired TeamRepository teamRepo;

    @GetMapping("/rosters")
    public Iterable<Roster> getRosters(){
        return rosterRepo.findAll();
    }

    @GetMapping("/test")
    public Boolean test(){
        Optional<Player> p = playerRepo.findById(1L);
        Player player = p.get();
        Set<Roster> rosters = player.getRosters();
        int l1 = rosters.size();
        for (Roster r : rosters){
            rosters.remove(r);
        }
        rosters = player.getRosters();
        int l2 = rosters.size();
        playerRepo.save(player);
        return l2 == l1;
    }


}
