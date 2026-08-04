<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="cashflowf.CashFlowRow" %>

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
    <meta charset="UTF-8">
    <title>月現金流預測表</title>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    </head>

<body>
    <h1>月現金流預測表</h1>

    <div>
        <form action="<%= request.getContextPath() %>/cashflowf" method="post">
            <p>
                開始月份：
                <input type="month" name="startMonth"
                       value="<%= startMonthValue %>"
                       required>
            </p>

            <p>
                結束月份：
                <input type="month" name="endMonth"
                       value="<%= endMonthValue %>"
                       required>
            </p>

            <p>
                每月固定支出：
                <input type="number" name="fixedExpense" step="0.01"
                       value="<%= fixedExpenseValue %>"
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
        if (rows != null) {
    %>

        <h2>現金流折線圖</h2>
		<!-- 生成cashFlowChart畫布 -->
        <div class="chart-area">
            <canvas id="cashFlowChart"></canvas>
        </div>

        <script>
        	//把row裡的資料轉成js陣列
            const labels = [
                <%
                    for (int i = 0; i < rows.size(); i++) {
                        CashFlowRow row = rows.get(i);
                %>
                        "<%= row.getMonth() %>"<%= i < rows.size() - 1 ? "," : "" %>
                <%
                    }
                %>
            ];

            const endingCashData = [
                <%
                    for (int i = 0; i < rows.size(); i++) {
                        CashFlowRow row = rows.get(i);
                %>
                        <%= row.getEndingCash() %><%= i < rows.size() - 1 ? "," : "" %>
                <%
                    }
                %>
            ];

            const minimumCashData = [
                <%
                    for (int i = 0; i < rows.size(); i++) {
                        CashFlowRow row = rows.get(i);
                %>
                        <%= row.getMinimumCash() %><%= i < rows.size() - 1 ? "," : "" %>
                <%
                    }
                %>
            ];

            const netCashFlowData = [
                <%
                    for (int i = 0; i < rows.size(); i++) {
                        CashFlowRow row = rows.get(i);
                %>
                        <%= row.getNetCashFlow() %><%= i < rows.size() - 1 ? "," : "" %>
                <%
                    }
                %>
            ];
			//0線
            const zeroLineData = labels.map(function() {
                return 0;
            });
			//找到cashFlowChart畫布
            const ctx = document.getElementById("cashFlowChart");

            new Chart(ctx, {
                type: "line",
                data: {
                    labels: labels,
                    datasets: [
                        {
                            label: "月底現金",
                            data: endingCashData,
                            borderColor: "blue",
                            backgroundColor: "blue",
                            pointBackgroundColor: "blue",
                            tension: 0.2
                        },
                        {
                            label: "月內最低現金",
                            data: minimumCashData,
                            borderColor: "orange",
                            backgroundColor: "orange",
                            pointBackgroundColor: "orange",
                            tension: 0.2
                        },
                        {
                            label: "本月淨現金流",
                            data: netCashFlowData,
                            borderColor: "green",
                            backgroundColor: "green",
                            pointBackgroundColor: "green",
                            tension: 0.2
                        },
                        {
                            label: "0 元警戒線",
                            data: zeroLineData,
                            borderColor: "red",
                            backgroundColor: "red",
                            pointBackgroundColor: "red",
                            borderDash: [6, 6],
                            pointRadius: 0
                        }
                    ]
                },
                options: {
                    responsive: true,
                    plugins: {
                        tooltip: {
                            callbacks: {
                                label: function(context) {
                                    return context.dataset.label + "：" + context.raw.toLocaleString();
                                }
                            }
                        }
                    },
                    scales: {
                        y: {
                            ticks: {
                                callback: function(value) {
                                    return value.toLocaleString();
                                }
                            }
                        }
                    }
                }
            });
        </script>

        <h2>現金流明細表</h2>

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