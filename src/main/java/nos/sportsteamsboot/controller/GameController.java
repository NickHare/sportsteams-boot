package nos.sportsteamsboot.controller;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import nos.sportsteamsboot.model.Game;
import nos.sportsteamsboot.service.GameService;
import nos.sportsteamsboot.view.DetailedView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameController {
    @Autowired
    GameService gameService;

    @GetMapping("")
    @JsonView(DetailedView.class)
    public Iterable<Game> getGames(){
        return gameService.getGames();
    }

    @GetMapping("/{id}")
    @JsonView(DetailedView.class)
    public Game getGame(@PathVariable("id") Long id){
        return gameService.getGame(id);
    }

    @PostMapping("")
    @JsonView(DetailedView.class)
    public Game postGame(@RequestBody @Validated Game game){
        return gameService.insertGame(game);
    }


}
