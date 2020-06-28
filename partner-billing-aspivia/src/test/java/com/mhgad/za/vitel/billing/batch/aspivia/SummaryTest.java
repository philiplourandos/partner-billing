package com.mhgad.za.vitel.billing.batch.aspivia;

import com.mhgad.za.vitel.billing.batch.aspivia.model.Summary;
import com.mhgad.za.vitel.billing.batch.aspivia.tasklet.SummaryOutputTasklet;
import com.mhgad.za.vitel.billing.batch.common.repo.PartnerBillingRepo;
import com.mhgad.za.vitel.billing.batch.common.repo.TestRepo;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class SummaryTest {

    private static final String OUT_FILE_NAME = "summary-cpt.csv";

    private final ClassPathResource aspiviaCostedBilling = new ClassPathResource("aspivia-costed-billing.csv");
    private final ClassPathResource summaryDataFile = new ClassPathResource("summary-calc-test.csv");

    private static final String SITE = "CPT";
    private static final Integer SITE_ID = 1;

    private static final Integer EXPECTED_RECORDS = 22;
    
    @Autowired
    private JobLauncher launcher;

    @Autowired
    private Job summaryJob;

    @Autowired
    private TestRepo testRepo;

    @Autowired
    private PartnerBillingRepo partnerRepo;

    @Test
    public void success() throws Exception {
        final File file = aspiviaCostedBilling.getFile();

        final File outputFile = new File(file.getParentFile(), OUT_FILE_NAME);

        final JobParametersBuilder paramBuilder = new JobParametersBuilder();
        paramBuilder.addString(AspiviaConst.PARAM_INPUT_FILE, file.getPath());
        paramBuilder.addString(AspiviaConst.PARAM_SITE, SITE);
        paramBuilder.addString(AspiviaConst.PARAM_OUTPUT_FILE_PATH, outputFile.getPath());

        JobExecution jobExec = launcher.run(summaryJob, paramBuilder.toJobParameters());

        assertEquals(ExitStatus.COMPLETED, jobExec.getExitStatus());
        
        Integer recordCount = testRepo.countAspivia();
        assertEquals(EXPECTED_RECORDS, recordCount);
        
        assertTrue(outputFile.exists());
    }
    
    @Test
    public void  failNoParams() throws Exception {
        JobParametersBuilder paramBuilder = new JobParametersBuilder();

        JobExecution jobExec = launcher.run(summaryJob, paramBuilder.toJobParameters());
        
        assertEquals(ExitStatus.FAILED.getExitCode(), jobExec.getExitStatus().getExitCode());
    }

    @Test
    public void failMissingParams() throws Exception {
        JobParametersBuilder paramBuilder = new JobParametersBuilder();

        paramBuilder.addString(AspiviaConst.PARAM_SITE, SITE);

        JobExecution jobExec = launcher.run(summaryJob, paramBuilder.toJobParameters());

        assertEquals(ExitStatus.FAILED.getExitCode(), jobExec.getExitStatus().getExitCode());
    }
    
    @Test
    public void successfullyGenerateTotals() throws IOException {
        final SummaryOutputTasklet summaryTasklet = new SummaryOutputTasklet();
        summaryTasklet.setPartnerRepo(partnerRepo);
        summaryTasklet.setSiteId(SITE_ID);

        final List<String> lines =
                Files.readAllLines(Paths.get(summaryDataFile.getURI()), StandardCharsets.UTF_8);

        final List<Summary> inputData = new ArrayList<>();
        final List<Summary> expectedValues = new ArrayList<>();

        lines.forEach(line -> {
            String[] values = line.split(",");

            Integer accCode = Integer.valueOf(values[0].trim());
            String partner = values[1].trim();
            Integer callCount = Integer.valueOf(values[2].trim());
            BigDecimal inAmount = new BigDecimal(values[3].trim());
            BigDecimal outAmount = new BigDecimal(values[4].trim());
            BigDecimal total = new BigDecimal(values[6].trim());

            Summary input = new Summary();
            input.setAccountCode(accCode);
            input.setPartner(partner);
            input.setNumberOfCalls(callCount);
            input.setMoneyIn(inAmount);
            input.setMoneyOut(outAmount);

            Summary expectation = new Summary();
            expectation.setAccountCode(accCode);
            expectation.setNumberOfCalls(callCount);
            expectation.setPartner(partner);
            expectation.setMoneyIn(inAmount);
            expectation.setMoneyOut(outAmount);
            expectation.setTotal(total);

            inputData.add(input);
            expectedValues.add(expectation);
        });

        summaryTasklet.calculateTotals(inputData);

        expectedValues.stream().forEach(e -> {
            assertTrue(expectedValues.contains(e));
        });
    }
}
