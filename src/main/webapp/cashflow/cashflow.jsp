<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="cashflow.CashFlowRow" %>

<!DOCTYPE html>
<html lang="zh-Hant" dir="ltr">
<head>
    <meta charset="UTF-8">
    <title>月現金流預測表</title>

    <head>
<body>
    <h1>月現金流預測表</h1>

    <div class="form-area">
        <form action="<%= request.getContextPath() %>/cashflow" method="post">
            <p>
                開始月份：
                <input type="month" name="startMonth"
                       value="<%= request.getAttribute("startMonth") == null ? "" : request.getAttribute("startMonth") %>"
                       required>
            </p>

            <p>
                結束月份：
                <input type="month" name="endMonth"
                       value="<%= request.getAttribute("endMonth") == null ? "" : request.getAttribute("endMonth") %>"
                       required>
            </p>

            <p>
                每月固定支出：
                <input type="number" name="fixedExpense" step="0.01"
                       value="<%= request.getAttribute("fixedExpense") == null ? "0" : request.getAttribute("fixedExpense") %>"
                       required>
            </p>

            <button type="submit">產生報表</button>
        </form>
    </div>

    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <p class="error"><%= error %></p>
    <%
        }
    %>

    <%
        List<CashFlowRow> rows = (List<CashFlowRow>) request.getAttribute("rows");
        DecimalFormat moneyFormat = new DecimalFormat("#,##0");

        if (rows != null) {
    %>
        <table border="1">
            <tr>
                <th>月份</th>
                <th>月初現金</th>
                <th>本月應收</th>
                <th>本月應付</th>
                <th>固定支出</th>
                <th>本月淨現金流</th>
                <th>月底現金</th>
                <th>月內最低現金</th>
            </tr>

            <%
                for (CashFlowRow row : rows) {
            %>
                <tr>
                    <td><%= row.getMonth() %></td>
                    <td><%= moneyFormat.format(row.getBeginningCash()) %></td>
                    <td><%= moneyFormat.format(row.getMonthlyReceivable()) %></td>
                    <td><%= moneyFormat.format(row.getMonthlyPayable()) %></td>
                    <td><%= moneyFormat.format(row.getFixedExpense()) %></td>
                    <td><%= moneyFormat.format(row.getNetCashFlow()) %></td>
                    <td><%= moneyFormat.format(row.getEndingCash()) %></td>
                    <td><%= moneyFormat.format(row.getMinimumCash()) %></td>
                </tr>
            <%
                }
            %>
        </table>
    <%
        }
    %>

    <p>
        <a href="<%= request.getContextPath() %>/accounts">回帳戶列表</a>
    </p>
</body>
</html>