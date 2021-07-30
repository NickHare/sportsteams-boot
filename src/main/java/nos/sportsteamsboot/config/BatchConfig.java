package nos.sportsteamsboot.config;

import nos.sportsteamsboot.batch.NbaRosterLoadTasklet;
import nos.sportsteamsboot.client.NbaRestClient;
import nos.sportsteamsboot.repository.TeamRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.batch.JpaBatchConfigurer;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig extends JpaBatchConfigurer {
    @Autowired private JobBuilderFactory jobBuilderFactory;
    @Autowired private StepBuilderFactory stepBuilderFactory;
    @Autowired private JobLauncher jobLauncher;
    @Autowired private JobRepository jobRepository;
    @Autowired private JobExplorer jobExplorer;
    @Autowired private JobOperator jobOperator;
    @Autowired private JobRegistry jobRegistry;

    @Autowired private TeamRepository teamRepository;
    @Autowired private NbaRestClient restClient;
    @Autowired private DataSource dataSource;
    @Autowired private EntityManagerFactory entityManagerFactory;
    @Autowired private PlatformTransactionManager transactionManager;

    public BatchConfig(BatchProperties properties, DataSource dataSource, TransactionManagerCustomizers transactionManagerCustomizers, EntityManagerFactory entityManagerFactory) {
        super(properties, dataSource, transactionManagerCustomizers, entityManagerFactory);
    }


    public JobLauncher createJobLauncher() throws Exception{
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
