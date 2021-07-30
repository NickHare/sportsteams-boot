package nos.sportsteamsboot.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class TestBatchConfig {
    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;

    @Bean
    Qualifier("testRosterLoadJob")
    public Job getTestRosterLoadJob({
        return  
    }


    @Bean
    public JobLauncherTestUtils getJobLauncherTestUtils() {
        return new JobLauncherTestUtils() {
            @Override
            @Autowired
            public void setJob(@Qualifier("rosterLoadJob") Job job) {
                super.setJob(job);
            }
        };
    }

    @Bean
    public NbaRosterLoadTasklet rosterLoadTasklet(){
        return new NbaRosterLoadTasklet();
    }

    @Bean
    public Step rosterLoadStep(){
        return stepBuilderFactory
                .get("rosterLoad")
                .tasklet(rosterLoadTasklet())
                .build();
    }

    @Bean
    @Qualifier("rosterLoadJob")
    public Job rosterLoadJob(){
        return jobBuilderFactory
                .get("rosterLoad")
                .incrementer((JobParameters p) -> {
                    Long id = (p == null || p.isEmpty())? 1L : p.getLong("id") + 1;
                    return new JobParametersBuilder()
                            .addLong("id", id)
                            .addLong("startTime", System.currentTimeMillis())
                            .toJobParameters();
                })
                .start(rosterLoadStep())
                .build();
    }

    @Bean
    @Qualifier("rosterLoad2Job")
    public Job rosterLoad2Job(){
        return jobBuilderFactory
                .get("rosterLoad")
                .incrementer((JobParameters p) -> {
                    Long id = (p == null || p.isEmpty())? 1L : p.getLong("id") + 1;
                    return new JobParametersBuilder()
                            .addLong("id", id)
                            .addLong("startTime", System.currentTimeMillis())
                            .toJobParameters();
                })
                .start(rosterLoadStep())
                .build();
    }

}
