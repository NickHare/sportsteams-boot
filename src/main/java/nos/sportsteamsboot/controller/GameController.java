package nos.sportsteamsboot.controller;

import com.fasterxml.jackson.annotation.JsonView;

import nos.sportsteamsboot.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import nos.sportsteamsboot.model.Game;
import nos.sportsteamsboot.service.GameService;
import nos.sportsteamsboot.view.PublicView;

@RestController
@RequestMapping("/games")
public class GameController {
    @Autowired
    GameService gameService;

    @GetMapping("")
    @JsonView(PublicView.class)
    public Iterable<Game> getGames(){
        return gameService.getGames();
    }

    @GetMapping("/{id}")
    @JsonView(PublicView.class)
    public Game getGame(@PathVariable("id") Long id){
        return gameService.getGame(id);
    }

    @PostMapping("")
    @JsonView(PublicView.class)
    public Game postGame(@RequestBody @Validated Game game){
        return gameService.insertGame(game);
    }


}
