<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-Hant" dir="ltr">
  <head>
 	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <meta charset="utf-8">
    <title>新增收款項目</title>
  </head>
  <body>
	<h1>新增收款項目</h1>
	<form action="<%=request.getContextPath()%>/addReceivable" method="post">
		<p>
			客戶名稱
			<input type="text" name="customerName" required="required">
		</p>
		<p>
			收款項目
			<input type="text" name="title" required="required">
		</p>
		<p>
			金額
			<input type="number" name="amount" step="0.01" required="required">
		</p>
		<p>	
			預計收款日
			<input type="date" name="expectedDate" required="required">
		</p>
		<p>
			<label>
		    	<input type="radio" name="status" value="UNPAID" checked>
		  	 	未付款
			</label>
		
			<label>
	    		<input type="radio" name="status" value="PAID">
	   	 		已付款
			</label>
		</p>
		<p>
			備註
			<input type="text" name="note" >
		</p>
		<button type="submit">新增</button>
		<p>
        	<a href="<%=request.getContextPath()%>/receivables">回到應收款資料表</a>
    	</p>
	</form>
	
  </body>
</html>

