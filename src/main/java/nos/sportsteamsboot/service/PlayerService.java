package nos.sportsteamsboot.service;

import nos.sportsteamsboot.model.Player;
import nos.sportsteamsboot.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired PlayerRepository playerRepository;

    public Player insertPlayer(Player player){
        Player p = null;
        try {
            p = playerRepository.save(player);
        } catch(Exception e){
            e.printStackTrace();
        }
        return p;
    }

    public Player getPlayer(Long id){
        Player p = null;
        try {
            p = playerRepository.findById(id).get();
        } catch(Exception e){
            e.printStackTrace();
        }
        return p;
    }

    public Iterable<Player> getPlayers(){
        Iterable<Player> p = null;
        try {
            p = playerRepository.findAll();
        } catch(Exception e){
            e.printStackTrace();
        }
        return p;
    }
}
