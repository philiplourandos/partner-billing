package com.mhgad.za.vitel.billing.batch.extract.decision;

import com.mhgad.za.vitel.billing.batch.extract.PartnerBillingConst;
import com.mhgad.za.vitel.billing.batch.extract.tasklet.SiteSupplierTasklet;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class NextSiteDecision implements JobExecutionDecider {

    private final SiteSupplierTasklet task;
    
    public NextSiteDecision(final SiteSupplierTasklet task) {
        this.task = task;
    }

    @Override
    public FlowExecutionStatus decide(final JobExecution jobExecution, final StepExecution stepExecution) {
        FlowExecutionStatus response = new FlowExecutionStatus(PartnerBillingConst.STATUS_CONTINUE);

        if (task.getSites().isEmpty()) {
            response = FlowExecutionStatus.COMPLETED;
        }

        return response;
    }
}
