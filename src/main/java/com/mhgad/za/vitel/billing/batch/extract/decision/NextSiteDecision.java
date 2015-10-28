package com.mhgad.za.vitel.billing.batch.extract.decision;

import com.mhgad.za.vitel.billing.batch.extract.PartnerBillingConst;
import com.mhgad.za.vitel.billing.batch.extract.tasklet.SiteSupplierTasklet;
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
    private SiteSupplierTasklet task;
    
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
