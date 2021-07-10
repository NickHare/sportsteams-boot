package nos.sportsteamsboot.service;

import nos.sportsteamsboot.model.Player;
import nos.sportsteamsboot.model.Team;
import nos.sportsteamsboot.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    @Autowired PlayerRepository playerRepository;

    public Player insertPlayer(Player player){
        return this.playerRepository.save(player);
    }

    public Optional<Player> getPlayer(Long id){
        return this.playerRepository.findById(id);
    }

    public Optional<Player> getPlayerByName(String name){
        return this.playerRepository.findByName(name);
    }

    public Optional<Player> getPlayerByExternalId(String extId){
        return this.playerRepository.findByName(extId);
    }

    public List<Player> getPlayers(){
        return this.playerRepository.findAll();
    }
}
