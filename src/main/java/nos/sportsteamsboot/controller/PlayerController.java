package nos.sportsteamsboot.controller;

import com.fasterxml.jackson.annotation.JsonView;

import nos.sportsteamsboot.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import nos.sportsteamsboot.model.Player;
import nos.sportsteamsboot.repository.PlayerRepository;
import nos.sportsteamsboot.view.PublicView;

@RestController
public class PlayerController {
    @Autowired PlayerRepository playerRepo;
    @Autowired PlayerService playerService;

    @GetMapping("/players")
    @JsonView(PublicView.class)
    public Iterable<Player> getPlayers(){
        return playerRepo.findAll();
    }

    @GetMapping(path="/players/{id}", produces = "application/json")
    @JsonView(PublicView.class)
    public Player getPlayer(@PathVariable("id") Long id) {
        return playerRepo.findById(id).orElse(new Player(""));
    }

    @PostMapping("/players")
    @JsonView(PublicView.class)
    public Player postPlayer(@RequestBody @Validated Player player){
        //Player p = playerRepo.getByName("Nick");
        return playerService.insertPlayer(player);
    }


}
