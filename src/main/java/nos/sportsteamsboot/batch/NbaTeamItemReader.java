package nos.sportsteamsboot.batch;


import nos.sportsteamsboot.client.NbaRestClient;
import nos.sportsteamsboot.model.Team;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NbaTeamItemReader implements ItemReader<Team>{

    @Autowired private NbaRestClient restClient;

    @Override
    public Team read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
