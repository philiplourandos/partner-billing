package com.mhgad.za.vitel.billing.batch.aspivia.tasklet;

import com.mhgad.za.vitel.billing.batch.aspivia.AspiviaConst;
import com.mhgad.za.vitel.billing.batch.aspivia.model.Summary;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author plourand
 */
public class SummaryOutputTasklet implements Tasklet {

    private static final Logger LOG = LogManager.getLogger(SummaryOutputTasklet.class);

    @Value("#{jobParameters['output.file.path']}")
    private String outputPath;

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

            Map<String, Summary> summaries = (Map<String, Summary>) execCtx.get(AspiviaConst.SUMMARY_KEY);

            summaries.entrySet().stream().forEach(val -> {
                Summary summary = val.getValue();

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
                builder.append(summary.getFinalTotal());
                builder.append('\n');
            });

            Path outputDir = Paths.get(outputPath);

            Files.write(outputDir, builder.toString().getBytes("UTF-8"));
        }

        return RepeatStatus.FINISHED;
    }
}
