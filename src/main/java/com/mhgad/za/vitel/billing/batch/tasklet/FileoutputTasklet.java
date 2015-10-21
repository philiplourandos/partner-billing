package com.mhgad.za.vitel.billing.batch.tasklet;

import com.mhgad.za.vitel.billing.batch.model.Site;
import com.mhgad.za.vitel.billing.batch.repo.PartnerBillingRepo;
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
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author plourand
 */
@Component
public class FileoutputTasklet implements Tasklet, InitializingBean {

    private static final Logger LOG = LogManager.getLogger(FileoutputTasklet.class);
    
    private static final String REPLACEMENT_VALUE = "site";
    
    @Autowired
    @Qualifier("fileoutReader")
    private JdbcPagingItemReader reader;
    
    @Autowired
    private PartnerBillingRepo repo;
    
    private final Queue<Site> sites;
    
    public FileoutputTasklet() {
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
        
        if (!sites.isEmpty()) {
            Site next = sites.poll();
            
            Map<String, String> searchParams = new HashMap<>();
            searchParams.put(REPLACEMENT_VALUE, next.getOutputFile());

            reader.setParameterValues(searchParams);

            response = RepeatStatus.CONTINUABLE;
        }
        
        return response;
    }
}
