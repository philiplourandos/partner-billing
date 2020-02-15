package com.mhgad.za.vitel.billing.batch.aspivia.model;

import java.math.BigDecimal;

/**
 *
 * @author plourand
 */
public class Summary {
    private Integer numberOfCalls = 0;
    private Integer accountCode;
    private String partner;
    private BigDecimal moneyIn = BigDecimal.ZERO;
    private BigDecimal moneyOut = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;

    public Summary() {
    }

    public Integer getNumberOfCalls() {
        return numberOfCalls;
    }

    public void incCallCount() {
        numberOfCalls++;
    }

    public Integer getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(Integer accountCode) {
        this.accountCode = accountCode;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public BigDecimal getMoneyIn() {
        return moneyIn;
    }

    public BigDecimal getMoneyOut() {
        return moneyOut;
    }

    public BigDecimal getPrelimTotal() {
        return moneyIn.add(moneyOut);
    }

    public void addInbound(BigDecimal amount) {
        moneyIn = moneyIn.add(amount);
    }
    
    public void addOutBound(BigDecimal amount) {
        moneyOut = moneyOut.add(amount);
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    // For testing only
    public void setMoneyIn(BigDecimal moneyIn) {
        this.moneyIn = moneyIn;
    }

    // For testing only
    public void setMoneyOut(BigDecimal moneyOut) {
        this.moneyOut = moneyOut;
    }

    // For testing only
    public void setNumberOfCalls(Integer numberOfCalls) {
        this.numberOfCalls = numberOfCalls;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        
        if (Summary.class.isAssignableFrom(obj.getClass())) {
            Summary comparison = (Summary) obj;
            
            isEqual = numberOfCalls.equals(comparison.numberOfCalls) 
                    && accountCode.equals(comparison.accountCode)
                    && partner.equals(comparison.partner)
                    && moneyIn.equals(comparison.moneyIn)
                    && moneyOut.equals(comparison.moneyOut)
                    && total.equals(comparison.total);
        }

        return isEqual;
    }
}
