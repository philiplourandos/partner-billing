package com.mhgad.za.vitel.billing.batch.aspivia.tasklet;

import com.mhgad.za.vitel.billing.batch.aspivia.AspiviaConst;
import com.mhgad.za.vitel.billing.batch.aspivia.model.Summary;
import com.mhgad.za.vitel.billing.batch.common.model.PartnerMapping;
import com.mhgad.za.vitel.billing.batch.common.repo.PartnerBillingRepo;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author plourand
 */
public class SummaryOutputTasklet implements Tasklet {

    private static final Logger LOG = LogManager.getLogger(SummaryOutputTasklet.class);

    @Value("#{jobParameters['output.file.path']}")
    private String outputPath;

    @Value("#{jobExecutionContext['site.id']}")
    private Integer siteId;

    @Autowired
    private PartnerBillingRepo partnerRepo;

    public SummaryOutputTasklet() {
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Map<String, Object> execCtx = chunkContext.getStepContext().getJobExecutionContext();

        if (!execCtx.containsKey(AspiviaConst.SUMMARY_KEY)) {
            LOG.error("Summaries not present!");

            throw new RuntimeException("Summaries not present");
        } else {
            StringBuilder builder = new StringBuilder(15000);
            builder.append(AspiviaConst.SUMMARY_HEADER);
            builder.append('\n');

            Map<Integer, Summary> summaries = (Map<Integer, Summary>) execCtx.get(AspiviaConst.SUMMARY_KEY);

            List<Summary> calcList = summaries.values()
                    .stream()
                    .sorted(
                        (val1, val2) -> val1.getPartner().compareTo(val2.getPartner()))
                    .collect(Collectors.toList());

            calculateTotals(calcList);

            calcList.forEach(summary -> {
                        builder.append(summary.getAccountCode());
                        builder.append(',');
                        builder.append(summary.getPartner());
                        builder.append(',');
                        builder.append(summary.getNumberOfCalls());
                        builder.append(',');
                        builder.append(summary.getMoneyOut());
                        builder.append(',');
                        builder.append(summary.getMoneyIn());
                        builder.append(',');
                        builder.append(summary.getPrelimTotal());
                        builder.append(',');
                        builder.append(summary.getTotal());
                        builder.append('\n');
                    });

            Path outputDir = Paths.get(outputPath);

            Files.write(outputDir, builder.toString().getBytes("UTF8"));
        }

        return RepeatStatus.FINISHED;
    }

    public void calculateTotals(List<Summary> summaries) {
        BigDecimal finalTotal = BigDecimal.ZERO;
        PartnerMapping previousPartner = null;
        Summary previousSummary = null;

        Iterator<Summary> summariesItr = summaries.iterator();

        while (summariesItr.hasNext()) {
            final Summary summary = summariesItr.next();

            PartnerMapping partner = partnerRepo.findPartnerByAccountCode(siteId, summary.getAccountCode());

            // 1'st entry
            if (previousPartner == null) {
                finalTotal = finalTotal.add(summary.getPrelimTotal());
            } else if (partner.getDisciplineGroupId().equals(previousPartner.getDisciplineGroupId())) {
                finalTotal = finalTotal.add(summary.getPrelimTotal());
            } else if (!partner.getDisciplineGroupId().equals(previousPartner.getDisciplineGroupId())) {
                previousSummary.setTotal(finalTotal);

                finalTotal = summary.getPrelimTotal();
            }

            if (!summariesItr.hasNext()) {
                summary.setTotal(finalTotal);
            }

            previousPartner = partner;
            previousSummary = summary;
        }
    }

    public void setPartnerRepo(PartnerBillingRepo partnerRepo) {
        this.partnerRepo = partnerRepo;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }
}
