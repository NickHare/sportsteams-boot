package nos.sportsteamsboot.service;

import nos.sportsteamsboot.model.Player;
import nos.sportsteamsboot.model.Team;
import nos.sportsteamsboot.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired PlayerRepository playerRepository;

    public Player insertPlayer(Player player){
        Player p = playerRepository.save(player);
        return p;
    }

    public Player getPlayer(Long id){
        Player p = playerRepository.findById(id).get();
        return p;
    }

    public Player getPlayerByName(String name){
        Player p = playerRepository.findByName(name).orElse(null);
        return p;
    }

    public Player getPlayerByExternalId(String extId){
        Player p = playerRepository.findByName(extId).orElse(null);
        return p;
    }

    public Iterable<Player> getPlayers(){
        Iterable<Player> p = playerRepository.findAll();
        return p;
    }
}
