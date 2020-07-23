package nos.sportsteamsboot.service;

import nos.sportsteamsboot.model.Player;
import nos.sportsteamsboot.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {
    @Autowired PlayerRepository playerRepository;

    @Transactional
    public Player insertPlayer(Player player){
        Player p = null;
        try {
            p = playerRepository.save(player);
        } catch(Exception e){
            System.out.println(e);
        }
        return p;
    }
}
