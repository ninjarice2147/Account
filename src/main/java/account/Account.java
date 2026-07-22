package account;

import java.math.BigDecimal;

public class Account {
    private int accId;
    private String bank;
    private String accName;
    private BigDecimal amount;
    private String updateDate;

    public Account(int accId, String bank, String accName, BigDecimal amount, String updateDate) {
        this.accId = accId;
        this.bank = bank;
        this.accName = accName;
        this.amount = amount;
        this.updateDate = updateDate;
    }

    public int getAccId() {
        return accId;
    }

    public String getBank() {
        return bank;
    }

    public String getAccName() {
        return accName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getUpdateDate() {
        return updateDate;
    }
}