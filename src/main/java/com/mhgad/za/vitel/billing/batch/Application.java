package com.mhgad.za.vitel.billing.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author plourand
 */
public class Application {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = 
                new AnnotationConfigApplicationContext("com.mhgad.za.vitel.billing.batch");
        
        final Job generateReports = context.getBean(Job.class);
        final JobLauncher launcher = context.getBean(JobLauncher.class);
        
        launcher.run(generateReports, new JobParametersBuilder().toJobParameters());
    }
}
