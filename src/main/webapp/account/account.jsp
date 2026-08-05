<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="account.Account" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>銀行帳戶管理</title>
</head>
<body>
    <h1>銀行帳戶管理</h1>

    <p>
        <a href="addAccount">
            <button type="button">新增帳戶</button>
        </a>
    </p>

    <hr>

    <h2>帳戶列表</h2>

    <table border="1">
        <tr>
            <th>編號</th>
            <th>銀行</th>
            <th>帳戶名稱</th>
            <th>金額</th>
            <th>更新時間</th>
            <th>功能</th>
        </tr>

        <%
            List<Account> accounts = (List<Account>) request.getAttribute("accounts");

            if (accounts != null) {
                for (Account account : accounts) {
                    String updateFormId = "updateForm" + account.getAccId();
                    String deleteFormId = "deleteForm" + account.getAccId();
        %>
                    <tr>
                        <td>
                            <form id="<%= updateFormId %>" action="<%=request.getContextPath()%>/updateAccount" method="post">
                                <input type="hidden" name="accId" value="<%= account.getAccId() %>">
                            </form>

                            <form id="<%= deleteFormId %>" action="<%=request.getContextPath()%>/deleteAccount" method="post">
                                <input type="hidden" name="accId" value="<%= account.getAccId() %>">
                            </form>

                            <%= account.getAccId() %>
                        </td>

                        <td>
                            <input form="<%= updateFormId %>" class="editable" type="text"
                                   name="bank" value="<%= account.getBank() %>" readonly required>
                        </td>

                        <td>
                            <input form="<%= updateFormId %>" class="editable" type="text"
                                   name="accName" value="<%= account.getAccName() %>" readonly required>
                        </td>

                        <td>
                            <input form="<%= updateFormId %>" class="editable" type="number"
                                   name="amount" step="0.01"
                                   value="<%= account.getAmount() %>" readonly required>
                        </td>

                        <td>
                            <%= account.getUpdateDate() %>
                        </td>

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
                const result = confirm("確定要刪除這筆帳戶資料嗎？");

                if (!result) {
                    event.preventDefault();
                }
            });
        });
    </script>
</body>
</html>