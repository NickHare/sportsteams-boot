package nos.sportsteamsboot.service;

import nos.sportsteamsboot.model.Roster;
import nos.sportsteamsboot.repository.RosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RosterService {
    @Autowired RosterRepository rosterRepository;

    public Roster insertRoster(Roster roster){
        Roster r = null;
        try {
            r = rosterRepository.save(roster);
        } catch(Exception e){
            System.out.println(e);
        }
        return r;
    }

    public Roster getRoster(Long id){
        Roster r = null;
        try {
            r = rosterRepository.findById(id).orElse(new Roster());
        } catch(Exception e){
            System.out.println(e);
        }
        return r;
    }

    public Iterable<Roster> getRosters(){
        Iterable<Roster> r = null;
        try {
            r = rosterRepository.findAll();
        } catch(Exception e){
            System.out.println(e);
        }
        return r;
    }
}
