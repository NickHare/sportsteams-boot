package nos.sportsteamsboot.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nos.sportsteamsboot.model.Roster;
import nos.sportsteamsboot.service.RosterService;
import nos.sportsteamsboot.view.PublicView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rosters")
public class RosterController {

    @Autowired
    RosterService rosterService;

    @GetMapping("")
    @JsonView(PublicView.class)
    public Iterable<Roster> getRosters(){
        return rosterService.getRosters();
    }

    @GetMapping("/{id}")
    @JsonView(PublicView.class)
    public Roster getRoster(@PathVariable("id") Long id){
        return rosterService.getRoster(id);
    }

    @PostMapping("")
    @JsonView(PublicView.class)
    public Roster postRoster(@RequestBody @Validated Roster roster){
        return rosterService.insertRoster(roster);
    }


}
