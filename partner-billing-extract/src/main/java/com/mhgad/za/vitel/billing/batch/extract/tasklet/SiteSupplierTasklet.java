package com.mhgad.za.vitel.billing.batch.extract.tasklet;

import com.mhgad.za.vitel.billing.batch.common.AppProps;
import com.mhgad.za.vitel.billing.batch.extract.model.Site;
import com.mhgad.za.vitel.billing.batch.common.repo.PartnerBillingRepo;
import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

/**
 *
 * @author plourand
 */
@Component
public class SiteSupplierTasklet implements Tasklet, InitializingBean {

    private static final Logger LOG = LogManager.getLogger(SiteSupplierTasklet.class);

    private static final String SITE_QUERY_PARAM = "site";
    private static final String START_DATE_QUERY_PARAM = "startdate";
    private static final String END_DATE_QUERY_PARAM = "enddate";

    @Autowired
    private AppProps appProps;
    
    @Autowired
    @Qualifier("fileoutReader")
    private JdbcCursorItemReader reader;

    @Autowired
    private PartnerBillingRepo repo;

    @Autowired
    private FlatFileItemWriter writer;

    @Autowired
    private AppProps props;

    private final Queue<Site> sites;

    public SiteSupplierTasklet() {
        sites = new LinkedList<>();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        sites.addAll(repo.findAllSites());

        LOG.info("Loaded {} sites.", sites.size());
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        RepeatStatus response = RepeatStatus.FINISHED;

        Site next = sites.poll();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long start = dateFormatter.parse(appProps.getStartDate()).getTime();
        long end = dateFormatter.parse(appProps.getEndDate()).getTime();

        reader.setPreparedStatementSetter((ps) -> {
            ps.setString(1, next.getName());
            ps.setDate(2, new Date(start));
            ps.setDate(3, new Date(end));
        });

        FileSystemResource outputFileRes = new FileSystemResource(
                new File(props.getOutputPath(), next.getOutputFile()));
        writer.setResource(outputFileRes);

        LOG.info("Writing file: {}", next.getOutputFile());

        return response;
    }

    public Queue<Site> getSites() {
        return sites;
    }
}
