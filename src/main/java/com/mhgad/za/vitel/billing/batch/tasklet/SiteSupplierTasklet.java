package com.mhgad.za.vitel.billing.batch.tasklet;

import com.mhgad.za.vitel.billing.batch.AppProps;
import com.mhgad.za.vitel.billing.batch.model.Site;
import com.mhgad.za.vitel.billing.batch.repo.PartnerBillingRepo;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
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

    private static final String REPLACEMENT_VALUE = "site";

    @Autowired
    @Qualifier("fileoutReader")
    private JdbcPagingItemReader reader;

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

        Map<String, String> searchParams = new HashMap<>();
        searchParams.put(REPLACEMENT_VALUE, next.getName());

        reader.setParameterValues(searchParams);

        FileSystemResource outputFileRes = new FileSystemResource(
                new File(props.getOutputPath(), next.getOutputFile()));
        writer.setResource(outputFileRes);

        return response;
    }

    public Queue<Site> getSites() {
        return sites;
    }
}
