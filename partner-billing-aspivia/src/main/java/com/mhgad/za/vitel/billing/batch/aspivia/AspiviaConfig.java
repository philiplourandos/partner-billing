package com.mhgad.za.vitel.billing.batch.aspivia;

import com.mhgad.za.vitel.billing.batch.aspivia.components.AspiviaFieldSetter;
import com.mhgad.za.vitel.billing.batch.aspivia.components.AspiviaPrepStatementSetter;
import com.mhgad.za.vitel.billing.batch.aspivia.components.SummaryProcessor;
import com.mhgad.za.vitel.billing.batch.aspivia.model.BillingItem;
import com.mhgad.za.vitel.billing.batch.aspivia.tasklet.SummaryOutputTasklet;
import com.mhgad.za.vitel.billing.batch.common.SqlConst;
import com.mhgad.za.vitel.billing.batch.common.repo.PartnerBillingRepo;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@ComponentScan(basePackages = {"com.mhgad.za.vitel.billing.batch.aspivia",
    "com.mhgad.za.vitel.billing.batch.common"})
@Configuration
public class AspiviaConfig {

    @Autowired
    private AspiviaProps appProps;
    
    @Autowired
    private PartnerBillingRepo billingRepo;

    @Bean
    @StepScope
    public FlatFileItemReader aspiviaFileReader(@Value("#{jobParameters['input.file']}") String inputFile) {
        final DefaultLineMapper lineMapper = new DefaultLineMapper();
        lineMapper.setFieldSetMapper(new AspiviaFieldSetter());
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer());

        final FlatFileItemReader reader = new FlatFileItemReader();
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper);
        reader.setResource(new FileSystemResource(inputFile));

        return reader;
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter aspiviaWriter(DataSource ds, AspiviaPrepStatementSetter setter) {
        final JdbcBatchItemWriter writer = new JdbcBatchItemWriter();
        writer.setDataSource(ds);
        writer.setItemPreparedStatementSetter(setter);
        writer.setSql(SqlConst.INSERT_INTO_ASPIVIA);

        return writer;
    }

    @Bean
    @StepScope
    public AspiviaPrepStatementSetter prepStatementSetter() {
        return new AspiviaPrepStatementSetter();
    }
    
    @Bean
    @StepScope
    public SummaryProcessor summaryProc() {
        return new SummaryProcessor();
    }

    @Bean
    @StepScope
    public SummaryOutputTasklet summaryTask() {
        return new SummaryOutputTasklet();
    }

    @Bean
    public Job createJob(JobBuilderFactory jobBuilder, StepBuilderFactory stepBuilders, DataSource ds, FlatFileItemReader reader) {
        final Step findSiteIdStep = stepBuilders.get("find.site.id").tasklet((contribution,  chunkContext) -> {
            String siteName = 
                    (String) chunkContext.getStepContext().getJobParameters().get(AspiviaConst.PARAM_SITE);

            Integer siteId = billingRepo.findSiteIdByName(siteName);

            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put(
                    AspiviaConst.SITE_ID, siteId);

            return RepeatStatus.FINISHED;
        }).build();

        final Step processFileStep = 
                stepBuilders.get("process.file").<BillingItem, BillingItem>chunk(appProps.getChunkSize())
                .reader(reader)
                .processor(summaryProc())
                .writer(aspiviaWriter(ds, prepStatementSetter())).build();

        Step outputSummary = stepBuilders.get("output.summary").tasklet(summaryTask()).build();

        return jobBuilder.get("process.aspivia").incrementer(new RunIdIncrementer())
                .start(findSiteIdStep)
                .next(processFileStep)
                .next(outputSummary).build();
    }
}
