package receivable;

import java.math.BigDecimal;

public class Receivable{
	private int id;
	private String customer_name;
	private String title;
	private BigDecimal amount;
	private String expected_date;
	private String status;
	private String note;
	private String created_at;
	private String updated_at;
	public Receivable(int id,String customer_name,String title,BigDecimal amount,
			String expected_date,String status,String note,String created_at,String updated_at) {
		this.id=id;
		this.customer_name=customer_name;
		this.title=title;
		this.amount=amount;
		this.expected_date=expected_date;
		this.status=status;
		this.note=note;
		this.created_at=created_at;
		this.updated_at=updated_at;
	}
	
	
}