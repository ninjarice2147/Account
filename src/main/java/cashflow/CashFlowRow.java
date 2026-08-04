package cashflow;

import java.math.BigDecimal;

public class CashFlowRow {
	private String month;
	private BigDecimal beginningCash;
	private BigDecimal monthlyReceivable;
	private BigDecimal monthlyPayable;
	private BigDecimal fixedExpense;
	private BigDecimal netCashFlow;
	private BigDecimal endingCash;
	private BigDecimal minimumCash;
	
	public CashFlowRow(String month,BigDecimal beginningCash,BigDecimal monthlyReceivable,BigDecimal monthlyPayable,
            BigDecimal fixedExpense, BigDecimal netCashFlow, BigDecimal endingCash, BigDecimal minimumCash) {
		this.month = month;
        this.beginningCash = beginningCash;
        this.monthlyReceivable = monthlyReceivable;
        this.monthlyPayable = monthlyPayable;
        this.fixedExpense = fixedExpense;
        this.netCashFlow = netCashFlow;
        this.endingCash = endingCash;
        this.minimumCash = minimumCash;
	}
	
	public String getMonth(){
		return month;
	}
	public BigDecimal getBeginningCash() {
        return beginningCash;
    }

    public BigDecimal getMonthlyReceivable() {
        return monthlyReceivable;
    }

    public BigDecimal getMonthlyPayable() {
        return monthlyPayable;
    }

    public BigDecimal getFixedExpense() {
        return fixedExpense;
    }

    public BigDecimal getNetCashFlow() {
        return netCashFlow;
    }

    public BigDecimal getEndingCash() {
        return endingCash;
    }

    public BigDecimal getMinimumCash() {
        return minimumCash;
    }

	
	
	
	
	
	
	
}
