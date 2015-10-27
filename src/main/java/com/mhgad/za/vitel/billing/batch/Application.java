package com.mhgad.za.vitel.billing.batch;

import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author plourand
 */
public class Application {
    private static final Logger LOG = LogManager.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = 
                new AnnotationConfigApplicationContext("com.mhgad.za.vitel.billing.batch");
        
        final Job generateReports = context.getBean(Job.class);
        final JobLauncher launcher = context.getBean(JobLauncher.class);

        JobParametersBuilder paramBuilder = new JobParametersBuilder();
        paramBuilder.addDate("run.date", new Date());

        LOG.info("Starting partner billing run.");
        JobExecution runStatus = launcher.run(generateReports, paramBuilder.toJobParameters());

        LOG.info("Run completed, status: %s", runStatus.getExitStatus());
    }
}
