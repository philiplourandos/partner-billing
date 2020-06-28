package com.mhgad.za.vitel.billing.batch.extract.biz;

import com.mhgad.za.vitel.billing.batch.extract.model.Cdr;
import java.text.DecimalFormat;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CdrProcessor implements ItemProcessor<Cdr, Cdr> {

    private static final String SIP = "SIP";

    private static final int COUNTER = 0;
    
    @Value("${is.inbound.cost}")
    private Double isInboundCost;

    public CdrProcessor() {
    }

    @Override
    public Cdr process(final Cdr item) throws Exception {
        final String channel = item.getChannel();

        // Calculate cost
        if (channel.startsWith(SIP)) {
            final Double cost = item.getBillsec() * isInboundCost;

            final DecimalFormat formatter = new DecimalFormat("########0.00");
            final String cdrCost = formatter.format(cost);

            item.setCost(Double.valueOf(cdrCost));
        }

        // Set unique id
        if (StringUtils.isEmpty(item.getUniqueid())) {
            final StringBuilder uuidGen = new StringBuilder(50);
            uuidGen.append(item.getCallDate().getTime());
            uuidGen.append('.');

            final DecimalFormat formatter = new DecimalFormat("000000");
            uuidGen.append(formatter.format(COUNTER));

            item.setUniqueid(uuidGen.toString());
        }
        
        return item;
    }
}
