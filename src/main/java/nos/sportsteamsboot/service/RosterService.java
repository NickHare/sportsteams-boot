package nos.sportsteamsboot.service;

import nos.sportsteamsboot.model.Roster;
import nos.sportsteamsboot.repository.RosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RosterService {
    @Autowired RosterRepository rosterRepository;

    public Roster insertRoster(Roster roster){
        return rosterRepository.save(roster);
    }

    public Roster getRoster(Long id){
        return rosterRepository.findById(id).orElse(new Roster());
    }

    public Iterable<Roster> getRosters(){
        return rosterRepository.findAll();
    }
}
