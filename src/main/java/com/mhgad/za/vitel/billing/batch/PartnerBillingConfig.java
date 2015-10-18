package com.mhgad.za.vitel.billing.batch;

import com.mhgad.za.vitel.billing.batch.repo.DbServersRepo;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author plourand
 */
@Configuration
@ComponentScan(basePackages = {"com.mhgad.za.vitel.billing.batch"})
@EnableBatchProcessing
public class PartnerBillingConfig {
    
}
