package com.mhgad.za.vitel.billing.batch.extract.tasklet;

import com.mhgad.za.vitel.billing.batch.extract.model.Site;
import com.mhgad.za.vitel.billing.batch.common.repo.PartnerBillingRepo;
import com.mhgad.za.vitel.billing.batch.extract.ExtractProps;
import com.mhgad.za.vitel.billing.batch.extract.PartnerBillingConst;
import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;

public class SiteSupplierTasklet implements Tasklet, InitializingBean {

    private static final Logger LOG = LogManager.getLogger(SiteSupplierTasklet.class);

    private final ExtractProps appProps;

    private final JdbcCursorItemReader reader;

    private final PartnerBillingRepo repo;

    private final FlatFileItemWriter writer;

    private final Queue<Site> sites;

    public SiteSupplierTasklet(final ExtractProps appProps, final JdbcCursorItemReader reader,
            final PartnerBillingRepo repo, final FlatFileItemWriter writer) {
        this.appProps = appProps;
        this.reader = reader;
        this.repo = repo;
        this.writer = writer;
        this.sites = new LinkedList<>();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        sites.addAll(repo.findAllSites());

        LOG.info("Loaded {} sites.", sites.size());
    }

    @Override
    public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext) throws Exception {
        RepeatStatus response = RepeatStatus.FINISHED;

        final Site next = sites.poll();

        final Map<String, Object> jobsParams = chunkContext.getStepContext().getJobParameters();
        final String startDate = (String) jobsParams.get(PartnerBillingConst.PARAM_START_DATE);
        final String endDate = (String) jobsParams.get(PartnerBillingConst.PARAM_END_DATE);

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        final long start = dateFormatter.parse(startDate).getTime();
        final long end = dateFormatter.parse(endDate).getTime();

        reader.setPreparedStatementSetter((ps) -> {
            ps.setString(1, next.getName());
            ps.setDate(2, new Date(start));
            ps.setDate(3, new Date(end));
        });

        final FileSystemResource outputFileRes = new FileSystemResource(
                new File(appProps.getOutputPath(), next.getOutputFile()));
        writer.setResource(outputFileRes);

        LOG.info("Writing file: {}", next.getOutputFile());

        return response;
    }

    public Queue<Site> getSites() {
        return sites;
    }
}
