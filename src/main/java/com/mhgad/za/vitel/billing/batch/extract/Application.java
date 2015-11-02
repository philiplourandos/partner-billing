package com.mhgad.za.vitel.billing.batch.extract;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
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

    @Parameter(names = {"--start", "-s"}, description = "", required = true)
    private String startDate;
    @Parameter(names = {"--end", "-e"}, description = "", required = true)
    private String endDate;

    public Application() {
    }

    public void run() throws Exception {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext("com.mhgad.za.vitel.billing.batch.extract");

        final Job generateReports = context.getBean(Job.class);
        final JobLauncher launcher = context.getBean(JobLauncher.class);

        JobParametersBuilder paramBuilder = new JobParametersBuilder();
        paramBuilder.addString(PartnerBillingConst.PARAM_START_DATE, startDate);
        paramBuilder.addString(PartnerBillingConst.PARAM_END_DATE, endDate);

        LOG.info("Starting partner billing run.");
        JobExecution runStatus = launcher.run(generateReports, paramBuilder.toJobParameters());

        LOG.info("Run completed, status: {}", runStatus.getExitStatus());
    }

    public static void main(String[] args) throws Exception {
        Application app = new Application();
        
        JCommander cmd = new JCommander(app, args);
        
        app.run();
    }
}
