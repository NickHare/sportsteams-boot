package nos.sportsteamsboot.config;

import nos.sportsteamsboot.batch.NbaTeamItemProcessor;
import nos.sportsteamsboot.batch.NbaTeamItemReader;
import nos.sportsteamsboot.batch.NbaTeamItemWriter;
import nos.sportsteamsboot.client.NbaRestClient;
import nos.sportsteamsboot.model.Team;
import nos.sportsteamsboot.repository.TeamRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {
    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;
    @Autowired private JobLauncher jobLauncher;
    @Autowired private JobRepository jobRepository;
    @Autowired private JobExplorer jobExplorer;
    @Autowired private JobOperator jobOperator;
    @Autowired private JobRegistry jobRegistry;

    @Autowired private TeamRepository teamRepository;
    @Autowired private NbaRestClient restClient;

    @Bean
    protected JobLauncher createJobLauncher() throws Exception{
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(this.jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
        postProcessor.setJobRegistry(this.jobRegistry);
        return postProcessor;
    }

    @Bean
    public Step teamLoadStep(){
        return stepBuilderFactory
                .get("teamLoad")
                .<Team, Team>chunk(5)
                .reader(new NbaTeamItemReader(this.restClient))
                .processor(new NbaTeamItemProcessor(this.teamRepository))
                .writer(new NbaTeamItemWriter(this.teamRepository))
                .build();
    }

    @Bean
    public Job rosterLoadJob(){
        return jobBuilderFactory
                .get("rosterLoad")
                .start(teamLoadStep())
                .build();
    }
}
