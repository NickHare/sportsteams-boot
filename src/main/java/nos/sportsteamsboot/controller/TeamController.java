package nos.sportsteamsboot.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nos.sportsteamsboot.model.Player;
import nos.sportsteamsboot.model.Team;
import nos.sportsteamsboot.service.TeamService;
import nos.sportsteamsboot.view.PublicView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
public class TeamController {
    @Autowired TeamService teamService;

    @GetMapping("")
    @JsonView(PublicView.class)
    public Iterable<Team> getTeams(){
        return teamService.getTeams();
    }

    @GetMapping("/{id}")
    @JsonView(PublicView.class)
    public Team getTeam(@PathVariable("id") Long id){
        return teamService.getTeam(id);
    }

    @PostMapping("")
    @JsonView(PublicView.class)
    public Team postTeam(@RequestBody @Validated Team team){
        return teamService.insertTeam(team);
    }
}
