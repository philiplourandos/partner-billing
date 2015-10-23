package com.mhgad.za.vitel.billing.batch.decision;

import com.mhgad.za.vitel.billing.batch.PartnerBillingConst;
import com.mhgad.za.vitel.billing.batch.tasklet.FileoutputTasklet;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author plourand
 */
@Component
public class NextSiteDecision implements JobExecutionDecider {

    @Autowired
    private FileoutputTasklet task;
    
    public NextSiteDecision() {
    }

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        FlowExecutionStatus response = new FlowExecutionStatus(PartnerBillingConst.STATUS_CONTINUE);

        if( task.getSites().isEmpty()) {
            response = FlowExecutionStatus.COMPLETED;
        }

        return response;
    }
}
