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

    <h2>新增帳戶</h2>

    <form action="addAccount" method="post">
        <p>
            銀行：
            <input type="text" name="bank" required>
        </p>

        <p>
            帳戶名稱：
            <input type="text" name="accName" required>
        </p>

        <p>
            金額：
            <input type="number" name="amount" step="0.01" required>
        </p>

        <button type="submit">新增</button>
    </form>

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
                            <form id="<%= updateFormId %>" action="updateAccount" method="post">
                                <input type="hidden" name="accId" value="<%= account.getAccId() %>">
                            </form>

                            <form id="<%= deleteFormId %>" action="deleteAccount" method="post">
                                <input type="hidden" name="accId" value="<%= account.getAccId() %>">
                            </form>

                            <%= account.getAccId() %>
                        </td>

                        <td>
                            <input form="<%= updateFormId %>" class="editable" type="text"
                                   name="bank" value="<%= account.getBank() %>" readonly>
                        </td>

                        <td>
                            <input form="<%= updateFormId %>" class="editable" type="text"
                                   name="accName" value="<%= account.getAccName() %>" readonly>
                        </td>

                        <td>
                            <input form="<%= updateFormId %>" class="editable" type="number"
                                   name="amount" step="0.01"
                                   value="<%= account.getAmount() %>" readonly>
                        </td>

                        <td>
                            <%= account.getUpdateDate() %>
                        </td>

                        <td>
                            <button type="button" class="edit-btn" data-form="<%= updateFormId %>">
                                修改
                            </button>

                            <button form="<%= updateFormId %>" type="submit"
                                    class="update-btn" data-form="<%= updateFormId %>" disabled>
                                更新
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
        const editButtons = document.querySelectorAll(".edit-btn");

        editButtons.forEach(function(button) {
            button.addEventListener("click", function() {
                const formId = button.dataset.form;

                const inputs = document.querySelectorAll('[form="' + formId + '"].editable');

                inputs.forEach(function(input) {
                    input.removeAttribute("readonly");
                });

                const updateButton = document.querySelector('[form="' + formId + '"].update-btn');
                updateButton.disabled = false;
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