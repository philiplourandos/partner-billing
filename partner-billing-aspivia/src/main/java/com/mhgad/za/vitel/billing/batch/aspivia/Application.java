package com.mhgad.za.vitel.billing.batch.aspivia;

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

    @Parameter(names = {"--input", "-i"}, description = "Aspivia costed data", required = true)
    private String inputFile;
    
    @Parameter(names = {"--site", "-s"}, description = "Site the input file pertains to CTP/JHB", required = true)
    private String site;
    
    @Parameter(names = {"--output", "-o"}, description = "Summary output file", required = true)
    private String summaryFile;
    
    public Application() {
    }

    public void run() throws Exception {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext("com.mhgad.za.vitel.billing.batch.aspivia");

        final Job loadAspiviaDataJob = context.getBean(Job.class);
        final JobLauncher launcher = context.getBean(JobLauncher.class);

        JobParametersBuilder paramBuilder = new JobParametersBuilder();
        paramBuilder.addString(AspiviaConst.PARAM_INPUT_FILE, inputFile);
        paramBuilder.addString(AspiviaConst.PARAM_SITE, site);
        paramBuilder.addString(AspiviaConst.PARAM_OUTPUT_FILE_PATH, summaryFile);

        JobExecution runStatus = launcher.run(loadAspiviaDataJob, paramBuilder.toJobParameters());

        LOG.info("Run completed, status: {}", runStatus.getExitStatus());
    }

    public static void main(String[] args) throws Exception {
        Application app = new Application();
        
        JCommander cmd = new JCommander(app, args);

        app.run();
    }
}
