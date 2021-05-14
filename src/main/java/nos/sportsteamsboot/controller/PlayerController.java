package nos.sportsteamsboot.controller;

import com.fasterxml.jackson.annotation.JsonView;

import nos.sportsteamsboot.service.PlayerService;
import nos.sportsteamsboot.view.DetailedView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import nos.sportsteamsboot.model.Player;
import nos.sportsteamsboot.view.DetailedView;

@RestController
@RequestMapping("/players")
public class PlayerController {
    @Autowired private PlayerService playerService;

    @GetMapping("")
    @JsonView(DetailedView.class)
    public Iterable<Player> getPlayers(){
        return playerService.getPlayers();
    }

    @GetMapping("/{id}")
    @JsonView(DetailedView.class)
    public Player getPlayer(@PathVariable("id") Long id) {
        return playerService.getPlayer(id);
    }

    @PostMapping("")
    @JsonView(DetailedView.class)
    public Player postPlayer(@RequestBody @Validated Player player){
        return playerService.insertPlayer(player);
    }


}
