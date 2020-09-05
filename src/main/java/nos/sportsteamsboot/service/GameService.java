package nos.sportsteamsboot.service;

import nos.sportsteamsboot.model.Game;
import nos.sportsteamsboot.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    @Autowired GameRepository gameRepository;

    public Game insertGame(Game game){
        Game g = null;
        try {
            g = gameRepository.save(game);
        } catch(Exception e){
            System.out.println(e);
        }
        return g;
    }

    public Game getGame(Long id){
        Game g = null;
        try {
            g = gameRepository.findById(id).orElse(new Game());
        } catch(Exception e){
            System.out.println(e);
        }
        return g;
    }

    public Iterable<Game> getGames(){
        Iterable<Game> g = null;
        try {
            g = gameRepository.findAll();
        } catch(Exception e){
            System.out.println(e);
        }
        return g;
    }
}
