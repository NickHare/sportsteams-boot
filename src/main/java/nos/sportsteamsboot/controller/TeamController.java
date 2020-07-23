package nos.sportsteamsboot.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nos.sportsteamsboot.model.Team;
import nos.sportsteamsboot.repository.TeamRepository;
import nos.sportsteamsboot.view.PublicView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {
    @Autowired TeamRepository teamRepo;

    @GetMapping("/teams")
    @JsonView(PublicView.class)
    public Iterable<Team> getTeams(){
        return teamRepo.findAll();
    }

}
