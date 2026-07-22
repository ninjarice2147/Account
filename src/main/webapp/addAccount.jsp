<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>新增帳戶</title>
</head>
<body>
    <h1>新增帳戶</h1>

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

    <p>
        <a href="accounts">回帳戶列表</a>
    </p>
</body>
</html>