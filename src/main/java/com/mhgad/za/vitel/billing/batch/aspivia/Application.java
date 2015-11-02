package com.mhgad.za.vitel.billing.batch.aspivia;

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

    private static final int EXPECTED_ARGS = 3;
    private static final int INDEX_ASPIVIA_BILLING_FILE = 0;
    private static final int INDEX_SITE = 1;
    private static final int INDEX_OUTPUT_SUMMARY_FILE = 2;
    
    public static void main(String[] args) throws Exception {
        if (args.length != EXPECTED_ARGS) {
            LOG.error("Unexpected number of arguments");

            System.exit(-1);
        } else {
            AnnotationConfigApplicationContext context = 
                    new AnnotationConfigApplicationContext("com.mhgad.za.vitel.billing.batch.aspivia");
            
            final Job loadAspiviaDataJob = context.getBean(Job.class);
            final JobLauncher launcher = context.getBean(JobLauncher.class);
            
            JobParametersBuilder paramBuilder = new JobParametersBuilder();
            paramBuilder.addString(AspiviaConst.PARAM_INPUT_FILE,
                    args[INDEX_ASPIVIA_BILLING_FILE]);
            paramBuilder.addString(AspiviaConst.PARAM_SITE, args[INDEX_SITE]);
            paramBuilder.addString(AspiviaConst.PARAM_OUTPUT_FILE_PATH,
                    args[INDEX_OUTPUT_SUMMARY_FILE]);

            JobExecution runStatus = launcher.run(loadAspiviaDataJob, paramBuilder.toJobParameters());

            LOG.info("Run completed, status: {}", runStatus.getExitStatus());
        }
    }
}
