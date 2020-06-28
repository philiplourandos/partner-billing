package com.mhgad.za.vitel.billing.batch.aspivia.components;

import com.mhgad.za.vitel.billing.batch.aspivia.AspiviaConst;
import com.mhgad.za.vitel.billing.batch.aspivia.AttributeEnum;
import com.mhgad.za.vitel.billing.batch.aspivia.model.BillingItem;
import com.mhgad.za.vitel.billing.batch.aspivia.model.Summary;
import com.mhgad.za.vitel.billing.batch.common.repo.PartnerBillingRepo;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class SummaryProcessor implements ItemProcessor<BillingItem, BillingItem> {

    private static final Logger LOG = LogManager.getLogger(SummaryProcessor.class);

    private final Map<Integer, Summary> summaries;

    @Autowired
    private PartnerBillingRepo repo;

    @Value("#{jobParameters['site']}")
    private String site;

    public SummaryProcessor() {
        summaries = new HashMap<>();
    }

    @Override
    public BillingItem process(BillingItem item) throws Exception {
        final Integer accountCode = item.getAccountCode();

        Summary required = null;

        if (!summaries.containsKey(accountCode)) {
            LOG.info("Creating summary for acc. code: {}", accountCode);
            
            required = new Summary();
            required.setAccountCode(accountCode);
            required.setPartner(repo.findPartnerName(site, accountCode));

            summaries.put(accountCode, required);
        } else {
            required = summaries.get(accountCode);
        }

        BigDecimal cost = item.getCost();

        if (AttributeEnum.INBOUND.equals(item.getAttribute())) {
            required.addInbound(cost);
        } else {
            required.addOutBound(cost);
            required.incCallCount();
        }

        return item;
    }

    @AfterStep
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOG.info("Storing summaries in execution context");

        stepExecution.getJobExecution().getExecutionContext().put(
                AspiviaConst.SUMMARY_KEY, summaries);

        return ExitStatus.COMPLETED;
    }
}
