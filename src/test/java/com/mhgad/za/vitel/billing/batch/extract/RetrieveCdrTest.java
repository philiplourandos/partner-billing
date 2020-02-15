package com.mhgad.za.vitel.billing.batch.extract;

import com.mhgad.za.vitel.billing.batch.common.TestConfiguration;
import com.mhgad.za.vitel.billing.batch.extract.model.Cdr;
import com.mhgad.za.vitel.billing.batch.common.repo.TestRepo;
import com.mhgad.za.vitel.billing.batch.extract.tasklet.DatasourceSupplierTasklet;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author plourand
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfiguration.class, PartnerBillingConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RetrieveCdrTest {
    private static final Logger LOG = LogManager.getLogger(RetrieveCdrTest.class);

    private static final int EXPECTED_INITIAL_DATASOURCE_COUNT = 3;
    private static final Integer EXPECTED_ROW_COUNT = 60;
    private static final int EXPECTED_CPT_FILE_LINES = 40;
    private static final int EXPECTED_JHB_FILE_LINES = 20;

    private static final String CDR_DST_CHANNEL_WITH_NO_UUID = "IAX2/jhblvgw05_is_voip_out-29085";

    private static final String START_DATE = "2000-01-01";
    private static final String END_DATE = "2016-01-10";
    
    private static final String CPT_OUTPUT_FILE = "Di_Mhg-CTAccounts_12345_1234";
    private static final String JHB_OUTPUT_FILE = "Di_Mhg-JBAccounts_12345_1234";

    @Autowired
    private DatasourceSupplierTasklet dsTasklet;

    @Autowired
    private Job retrieveCdrsJob;

    @Autowired
    private JobLauncher launcher;
    
    @Autowired
    private TestRepo testRepo;

    @Autowired
    private EmbeddedDatabase ds;

    @Test
    public void success() throws JobExecutionException, IOException {
        assertEquals(EXPECTED_INITIAL_DATASOURCE_COUNT, dsTasklet.getDatasources().size());

        JobParametersBuilder paramBuilder = new JobParametersBuilder();
        paramBuilder.addString(PartnerBillingConst.PARAM_START_DATE, START_DATE);
        paramBuilder.addString(PartnerBillingConst.PARAM_END_DATE, END_DATE);

        JobExecution jobRun = launcher.run(retrieveCdrsJob, paramBuilder.toJobParameters());
        assertEquals(BatchStatus.COMPLETED, jobRun.getStatus());

        assertTrue(dsTasklet.getDatasources().isEmpty());
        
        final Integer count = testRepo.countCdrs();
        assertEquals(EXPECTED_ROW_COUNT, count);
        
        List<Cdr> cdrWithNoUuidInitially = testRepo.findByDestChannel(CDR_DST_CHANNEL_WITH_NO_UUID);
        assertNotNull(cdrWithNoUuidInitially);
        assertFalse(cdrWithNoUuidInitially.isEmpty());
        assertNotNull(cdrWithNoUuidInitially.get(0).getUniqueid());

        final ClassPathResource cptRes = new ClassPathResource(CPT_OUTPUT_FILE);
        final ClassPathResource jhbRes = new ClassPathResource(JHB_OUTPUT_FILE);

        assertEquals(EXPECTED_CPT_FILE_LINES, Files.readAllLines(Paths.get(cptRes.getURI())).size());
        assertEquals(EXPECTED_JHB_FILE_LINES, Files.readAllLines(Paths.get(jhbRes.getURI())).size());
    }
    
    @Test
    public void failNoParams() throws Exception {
        JobParametersBuilder paramBuilder = new JobParametersBuilder();

        JobExecution jobRun = launcher.run(retrieveCdrsJob, paramBuilder.toJobParameters());
        assertEquals(BatchStatus.FAILED, jobRun.getStatus());
    }

    @Test
    public void failMissingParams() throws Exception {
        JobParametersBuilder paramBuilder = new JobParametersBuilder();
        paramBuilder.addString(PartnerBillingConst.PARAM_START_DATE, START_DATE);

        JobExecution jobRun = launcher.run(retrieveCdrsJob, paramBuilder.toJobParameters());
        assertEquals(BatchStatus.FAILED, jobRun.getStatus());
    }
}
