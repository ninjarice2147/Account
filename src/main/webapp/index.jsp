<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zh-Hant" dir="ltr">
<head>
    <meta charset="UTF-8">
    <title>財務管理主控系統</title>

    <style>
        body {
            font-family: Arial, "Microsoft JhengHei", sans-serif;
            margin: 0;
            background-color: #f4f6f8;
        }

        .header {
            background-color: #1f4e79;
            color: white;
            padding: 24px 40px;
        }

        .header h1 {
            margin: 0;
        }

        .header p {
            margin: 8px 0 0 0;
            color: #dbe9f6;
        }

        .container {
            padding: 40px;
        }

        .menu {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 24px;
            max-width: 900px;
        }

        .card {
            background-color: white;
            border: 1px solid #dddddd;
            padding: 24px;
            border-radius: 8px;
        }

        .card h2 {
            margin-top: 0;
            color: #1f4e79;
        }

        .card p {
            color: #555555;
            line-height: 1.6;
        }

        .card a {
            display: inline-block;
            margin-top: 12px;
            padding: 10px 18px;
            background-color: #1f4e79;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }

        .card a:hover {
            background-color: #163a59;
        }
    </style>
</head>

<body>
    <div class="header">
        <h1>主控系統</h1>
        <p>帳戶、應收款、應付款與現金流預測管理</p>
    </div>

    <div class="container">
        <div class="menu">

            <div class="card">
                <h2>銀行帳戶管理</h2>
                <p>
                    查看目前各銀行帳戶金額，可以新增、修改、刪除帳戶資料。
                </p>
                <a href="<%= request.getContextPath() %>/accounts">進入帳戶管理</a>
            </div>

            <div class="card">
                <h2>應收款管理</h2>
                <p>
                    管理預計收款資料，例如客戶名稱、收款項目、金額與預計收款日。
                </p>
                <a href="<%= request.getContextPath() %>/receivables">進入應收款管理</a>
            </div>

            <div class="card">
                <h2>應付款管理</h2>
                <p>
                    管理預計付款資料，例如付款對象、付款項目、金額與預計付款日。
                </p>
                <a href="<%= request.getContextPath() %>/payables">進入應付款管理</a>
            </div>

            <div class="card">
                <h2>月現金流預測表</h2>
                <p>
                    依照帳戶現金、應收款、應付款與固定支出，產生月現金流預測表與折線圖。
                </p>
                <a href="<%= request.getContextPath() %>/cashflow">進入現金流預測</a>
            </div>
            <div class="card">
                <h2>自我介紹</h2>
                <p>
                    自我介紹網站
                </p>
                <a href="<%= request.getContextPath() %>/introduce/html/home_page.html">進入自我介紹頁面</a>
            </div>

        </div>
    </div>
</body>
</html>