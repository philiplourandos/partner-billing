package com.mhgad.za.vitel.billing.batch;

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

    private static final int EXPECTED_ARG_COUNT = 2;
    
    public static void main(String[] args) throws Exception {
        
        if (args.length != EXPECTED_ARG_COUNT) {
            LOG.error("cdr.start.date and cdr.end.date values must be passed in");
        } else {
            AnnotationConfigApplicationContext context = 
                    new AnnotationConfigApplicationContext("com.mhgad.za.vitel.billing.batch");

            final Job generateReports = context.getBean(Job.class);
            final JobLauncher launcher = context.getBean(JobLauncher.class);

            JobParametersBuilder paramBuilder = new JobParametersBuilder();
            paramBuilder.addString(PartnerBillingConst.PARAM_START_DATE, args[0]);
            paramBuilder.addString(PartnerBillingConst.PARAM_END_DATE, args[1]);

            LOG.info("Starting partner billing run.");
            JobExecution runStatus = launcher.run(generateReports, paramBuilder.toJobParameters());

            LOG.info("Run completed, status: {}", runStatus.getExitStatus());
        }
    }
}
