package payable;

import java.math.BigDecimal;

public class Payable{
	private int id;
	private String vendorName;
	private String title;
	private BigDecimal amount;
	private String expectedDate;
	private String status;
	private String note;
	private String createdAt;
	private String updatedAt;
	public Payable(int id,String vendorName,String title,BigDecimal amount,
			String expectedDate,String status,String note,String createdAt,String updatedAt) {
		this.id=id;
		this.vendorName=vendorName;
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
	public String getvendorName() {
		return vendorName;
	}
	public String getTitle() {
		return title;
	}
	public BigDecimal getAmount(){
		return amount;
	}
	public String getExpectedDate() {
		return expectedDate;
	}
	public String getStatus() {
		return status;
	}
	public String getNote() {
		return note;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public  String getUpdated() {
		return updatedAt;
	}
	
}