package receivable;

import java.math.BigDecimal;

public class Receivable{
	private int id;
	private String customerName;
	private String title;
	private BigDecimal amount;
	private String expectedDate;
	private String status;
	private String note;
	private String createdAt;
	private String updatedAt;
	public Receivable(int id,String customerName,String title,BigDecimal amount,
			String expectedDate,String status,String note,String createdAt,String updatedAt) {
		this.id=id;
		this.customerName=customerName;
		this.title=title;
		this.amount=amount;
		this.expectedDate=expectedDate;
		this.status=status;
		this.note=note;
		this.createdAt=createdAt;
		this.updatedAt=updatedAt;
	}
	public int getId(){
		return id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String title() {
		return title;
	}
	public BigDecimal amount(){
		return amount;
	}
	public String expectedDate() {
		return expectedDate;
	}
	public String status() {
		return status;
	}
	public String note() {
		return note;
	}
	public String createdAt() {
		return createdAt;
	}
	public  String updated() {
		return updatedAt;
	}
	
}