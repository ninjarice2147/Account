<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="payable.Payable" %>
<!DOCTYPE html>
<html lang="zh-Hant" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>應付款資料表</title>
  </head>
  <body>
	<h1>應付款資料表</h1>
	<p>
		<a href="<%=request.getContextPath()%>/addPayable">
			<button type="button">新增應付資料</button>
		</a>
	</p>
	<hr>
	<h2>應付列表</h2>
	<table border="1">
		<tr>
			<th>id</th>
			<th>供應商或付款對象名稱</th>
			<th>收款項目</th>
			<th>應付金額</th>
			<th>預計付款日</th>
			<th>是否已付款</th>
			<th>備註</th>
			<th>建立時間</th>
			<th>更改時間</th>
		</tr>
		<% 
			List<Payable> payables=(List<Payable>)request.getAttribute("payables");
			if(payables!=null){
				for(Payable payable:payables){
						String updateFormId="updateForm"+payable.getId();
						String deleteFormId="deleteForm"+payable.getId();
						%>
				<tr>
					<td>
						<form id="<%=updateFormId%>" action="<%=request.getContextPath()%>/updatePayable"
						method="post">
							<input type="hidden" name="id" value="<%=payable.getId()%>">
						</form>
						<form id="<%=deleteFormId%>" action="<%=request.getContextPath()%>/deletePayable"
						method="post">
							<input type="hidden" name="id" value="<%=payable.getId()%>">
						</form>
					</td>
					<td>
						<input form="<%=updateFormId%>"type="text" name="vendorName" 
						value="<%=payable.getvendorName()%>" class="editable" readonly required>
					</td>
					<td>
						<input form="<%=updateFormId%>"type="text" name="title" 
						value="<%=payable.getTitle()%>" class="editable" readonly required>
					</td>
					<td>
						<input form="<%=updateFormId%>"type="number" name="amount" 
						value="<%=payable.getAmount()%>" class="editable" step="0.01"readonly required>
					</td>
					<td>
						<input form="<%=updateFormId%>"type="date" name="expectedDate" 
						value="<%=payable.getExpectedDate()%>" class="editable" readonly required>
					</td>
					<td>
						<input form="<%=updateFormId%>"type="text" name="status" 
						value="<%=payable.getStatus()%>" class="editable" readonly required>
					</td>
					<td>
						<input form="<%=updateFormId%>"type="text" name="note" 
						value="<%=payable.getNote()%>" class="editable" readonly>
					</td>
					<td><%=payable.getCreatedAt()%></td>
					<td><%=payable.getUpdatedAt()%></td>
					<td>
						<button type="button" class="edit-update-btn"
						data-form="<%=updateFormId%>" data-mode="edit">
							修改
						</button>
						<button form="<%=deleteFormId%>" type="submit"
						class="delete-btn">
							刪除
						</button>
					</td>
				
				</tr>		
			<% }
				
			}%>
		
		
		
		
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
