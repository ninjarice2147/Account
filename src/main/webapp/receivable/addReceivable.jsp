<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-Hant" dir="ltr">
  <head>
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
			狀態(是否已收)
			<input type="text" name="status" required="required">
		</p>
		<p>
			備註
			<input type="text" name="note" >
		</p>
		<button type="submit">新增</button>
	</form>
	
  </body>
</html>

