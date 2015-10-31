package com.mhgad.za.vitel.billing.batch.summary;

import com.mhgad.za.vitel.billing.batch.aspivia.AspiviaConfig;
import com.mhgad.za.vitel.billing.batch.aspivia.AspiviaConst;
import com.mhgad.za.vitel.billing.batch.common.TestConfiguration;
import java.io.File;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

/**
 *
 * @author plourand
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AspiviaConfig.class, TestConfiguration.class})
@Rollback(true)
public class SummaryTest {

    private static final String OUT_FILE_NAME = "summary-cpt.csv";
    
    private final ClassPathResource inputFile = new ClassPathResource("aspivia-costed-billing.csv");
    
    private static final String SITE = "CPT";
    
    @Autowired
    private JobLauncher launcher;
    
    @Autowired
    private Job summaryJob;
    
    @Autowired
    private EmbeddedDatabase ds;
    
    @Test
    public void success() throws Exception {
        File file = inputFile.getFile();
        
        File outputFile = new File(file.getParentFile(), OUT_FILE_NAME);
        
        JobParametersBuilder paramBuilder = new JobParametersBuilder();
        paramBuilder.addString(AspiviaConst.PARAM_INPUT_FILE, file.getPath());
        paramBuilder.addString(AspiviaConst.PARAM_SITE, SITE);
        paramBuilder.addString(AspiviaConst.PARAM_OUTPUT_FILE_PATH, outputFile.getPath());

        JobExecution jobExec = launcher.run(summaryJob, paramBuilder.toJobParameters());

        assertEquals(ExitStatus.COMPLETED, jobExec.getExitStatus());
    }
    
    @After
    public void cleanup() {
        ds.shutdown();
    }
}
