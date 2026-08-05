<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="payable.Payable" %>

<!DOCTYPE html>
<html lang="zh-Hant" dir="ltr">
<head>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <meta charset="utf-8">
    <title>應付款資料表</title>
</head>
<body>
    <h1>應付款資料表</h1>
    <div class="page-actions">
    	<a class="secondary" href="<%= request.getContextPath() %>/">回主控畫面</a>
    	<a href="<%= request.getContextPath() %>/addPayable">新增應付資料</a>
	</div>


    <hr>

    <h2>應付列表</h2>

    <table border="1">
        <tr>
            <th>id</th>
            <th>付款對象名稱</th>
            <th>收款項目</th>
            <th>應付金額</th>
            <th>預計付款日</th>
            <th>是否已付款</th>
            <th>備註</th>
            <th>建立時間</th>
            <th>更改時間</th>
            <th>功能</th>
        </tr>

        <%
            List<Payable> payables = (List<Payable>) request.getAttribute("payables");

            if (payables != null) {
                for (Payable payable : payables) {
                    String updateFormId = "updateForm" + payable.getId();
                    String deleteFormId = "deleteForm" + payable.getId();

                    String statusText = "PAID".equals(payable.getStatus()) ? "已付款" : "未付款";
        %>
                    <tr>
                        <td>
                            <form id="<%= updateFormId %>"
                                  action="<%= request.getContextPath() %>/updatePayable"
                                  method="post">
                                <input type="hidden" name="id" value="<%= payable.getId() %>">
                            </form>

                            <form id="<%= deleteFormId %>"
                                  action="<%= request.getContextPath() %>/deletePayable"
                                  method="post">
                                <input type="hidden" name="id" value="<%= payable.getId() %>">
                            </form>

                            <%= payable.getId() %>
                        </td>

                        <td>
                            <input form="<%= updateFormId %>" type="text" name="vendorName"
                                   value="<%= payable.getvendorName() %>"
                                   class="editable" readonly required>
                        </td>

                        <td>
                            <input form="<%= updateFormId %>" type="text" name="title"
                                   value="<%= payable.getTitle() %>"
                                   class="editable" readonly required>
                        </td>

                        <td>
                            <input form="<%= updateFormId %>" type="number" name="amount"
                                   value="<%= payable.getAmount() %>"
                                   class="editable" step="0.01" readonly required>
                        </td>

                        <td>
                            <input form="<%= updateFormId %>" type="date" name="expectedDate"
                                   value="<%= payable.getExpectedDate() %>"
                                   class="editable" readonly required>
                        </td>

                        <td>
                            <span class="status-text">
                                <%= statusText %>
                            </span>

                            <span class="status-radio" style="display:none;">
                                <label>
                                    <input form="<%= updateFormId %>" type="radio"
                                           name="status" value="UNPAID"
                                           <%= "UNPAID".equals(payable.getStatus()) ? "checked" : "" %>>
                                    未付款
                                </label>

                                <label>
                                    <input form="<%= updateFormId %>" type="radio"
                                           name="status" value="PAID"
                                           <%= "PAID".equals(payable.getStatus()) ? "checked" : "" %>>
                                    已付款
                                </label>
                            </span>
                        </td>

                        <td>
                            <input form="<%= updateFormId %>" type="text" name="note"
                                   value="<%= payable.getNote() %>"
                                   class="editable" readonly>
                        </td>

                        <td><%= payable.getCreatedAt() %></td>
                        <td><%= payable.getUpdatedAt() %></td>

                        <td>
                            <button type="button" class="edit-update-btn"
                                    data-form="<%= updateFormId %>"
                                    data-mode="edit">
                                修改
                            </button>

                            <button form="<%= deleteFormId %>" type="submit"
                                    class="delete-btn">
                                刪除
                            </button>
                        </td>
                    </tr>
        <%
                }
            }
        %>
    </table>

    <script>
        const editUpdateButtons = document.querySelectorAll(".edit-update-btn");

        editUpdateButtons.forEach(function(button) {
            button.addEventListener("click", function() {
                const formId = button.dataset.form;
                const mode = button.dataset.mode;

                if (mode === "edit") {
                    const inputs = document.querySelectorAll('[form="' + formId + '"].editable');

                    inputs.forEach(function(input) {
                        input.removeAttribute("readonly");
                    });

                    const row = button.closest("tr");
                    const statusText = row.querySelector(".status-text");
                    const statusRadio = row.querySelector(".status-radio");

                    statusText.style.display = "none";
                    statusRadio.style.display = "inline";

                    button.textContent = "更新";
                    button.dataset.mode = "update";
                } else {
                    const updateForm = document.getElementById(formId);

                    if (updateForm.requestSubmit) {
                        updateForm.requestSubmit();
                    } else {
                        updateForm.submit();
                    }
                }
            });
        });

        const deleteButtons = document.querySelectorAll(".delete-btn");

        deleteButtons.forEach(function(button) {
            button.addEventListener("click", function(event) {
                const result = confirm("確定要刪除這筆應付款資料嗎？");

                if (!result) {
                    event.preventDefault();
                }
            });
        });
    </script>
</body>
</html>