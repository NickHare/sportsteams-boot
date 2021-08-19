package nos.sportsteamsboot.batch;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.Job;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

@SpringBatchTest
@SpringBootTest
public class NbaRosterLoadTest {

    @Autowired
    @Qualifier("rosterLoad")
    private Job job;

    @Autowired
    private JobLauncherTestUtils jobLauncherUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryUtils;

    @Test
    public void rosterLoadTest(){
//        Mockito.mock()
        NbaRosterLoadTasklet tasklet = new NbaRosterLoadTasklet();
        System.out.println("!!!-TEST");
    }
}
