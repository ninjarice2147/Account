<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="cashflow.CashFlowRow" %>
<%
    List<CashFlowRow> rows = (List<CashFlowRow>) request.getAttribute("rows");
    DecimalFormat moneyFormat = new DecimalFormat("#,##0");

    String startMonthValue = request.getAttribute("startMonth") == null
            ? ""
            : request.getAttribute("startMonth").toString();

    String endMonthValue = request.getAttribute("endMonth") == null
            ? ""
            : request.getAttribute("endMonth").toString();

    String fixedExpenseValue = request.getAttribute("fixedExpense") == null
            ? "0"
            : request.getAttribute("fixedExpense").toString();
%>
<!DOCTYPE html>
<html lang="zh-Hant" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>月現金流預測表</title>
  </head>
  <body>
	<h1>月現金流預測表</h1>
	<div>
		<form action="<%= request.getContextPath() %>/cashflow" method="post">
            <p>
                開始月份：
                <input type="month" name="startMonth"value="<%= startMonthValue %>"required>
            </p>
            
            <p>
                結束月份：
                <input type="month" name="endMonth"value="<%= endMonthValue %>"required>
            </p>

            <p>
                每月固定支出：
                <input type="number" name="fixedExpense" step="0.01"value="<%= fixedExpenseValue %>"required>
            </p>

            <button type="submit">產生報表</button>
        </form>
	</div>
	
	
	
  </body>
</html>
