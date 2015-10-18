package com.mhgad.za.vitel.billing.batch.decision;

import com.mhgad.za.vitel.billing.batch.PartnerBillingConst;
import com.mhgad.za.vitel.billing.batch.tasklet.DatasourceSupplierTasklet;
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
public class NextDatasourceDecision implements JobExecutionDecider {

    @Autowired
    private DatasourceSupplierTasklet dsSupplier;

    public NextDatasourceDecision() {
    }

    @Override
    public FlowExecutionStatus decide(JobExecution je, StepExecution se) {
        FlowExecutionStatus response = new FlowExecutionStatus(PartnerBillingConst.STATUS_CONTINUE);

        if (dsSupplier.getDatasources().isEmpty()) {
            response = FlowExecutionStatus.COMPLETED;
        }

        return response;
    }
}
