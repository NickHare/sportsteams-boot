package nos.sportsteamsboot.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nos.sportsteamsboot.client.NbaRestClient;
import nos.sportsteamsboot.model.Roster;
import nos.sportsteamsboot.service.RosterService;
import nos.sportsteamsboot.view.DetailedView;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rosters")
public class RosterController {

    @Autowired
    RosterService rosterService;

    @Autowired
    NbaRestClient restClient;

    @GetMapping("")
    @JsonView(DetailedView.class)
    public Iterable<Roster> getRosters(){
        return rosterService.getRosters();
    }

    @GetMapping("/{id}")
    @JsonView(DetailedView.class)
    public Roster getRoster(@PathVariable("id") Long id){
        return rosterService.getRoster(id).orElse(null);
    }

    @PostMapping("")
    @JsonView(DetailedView.class)
    public Roster postRoster(@RequestBody @Validated Roster roster){
        return rosterService.insertRoster(roster);
    }

    @GetMapping("/scores")
    @JsonView(DetailedView.class)
    public String score(){
        try{
            return restClient.getScores();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
