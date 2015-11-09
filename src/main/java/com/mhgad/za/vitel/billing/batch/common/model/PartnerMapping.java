package com.mhgad.za.vitel.billing.batch.common.model;

/**
 *
 * @author plourand
 */
public class PartnerMapping {

    private String name;
    private Integer accountCode;

    private Integer disciplineGroupId;

    public PartnerMapping() {
    }

    public Integer getDisciplineGroupId() {
        return disciplineGroupId;
    }

    public String getName() {
        return name;
    }

    public void setDisciplineGroupId(Integer disciplineGroupId) {
        this.disciplineGroupId = disciplineGroupId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(Integer accountCode) {
        this.accountCode = accountCode;
    }
}
