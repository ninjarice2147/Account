<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="receivable.Receivable" %>
<!DOCTYPE html>
<html lang="zh-Hant" dir="ltr">
<head>
	<meta charset="utf-8">
	<title>應收款資料表</title>
</head>
<body>
	<h1>應收款資料表</h1>
	<p>
  		<a href="addReceivable">
  			<button type="button" >新增</button>
  		</a>
  	</p>
	<hr>
	<h2>收款列表</h2>
	<table border="1">
		<tr>
			<th>id</th>
			<th>客戶名稱</th>
			<th>收款項目</th>
			<th>應收金額</th>
			<th>預計收款日</th>
			<th>是否已收取</th>
			<th>備註</th>
			<th>建立時間</th>
			<th>更改時間</th>
		</tr>
		<% 
			List<Receivable> receivables=(List<Receivable>)request.getAttribute("receivables");
			
			if(receivables!=null){
				for (Receivable receivable:receivables){
					String updateFormId="updateForm" + receivable.getId();
					String deleteFormId="deleteForm" + receivable.getId();
					%>
					<tr>
						<td>
							<form id="<%=updateFormId%>" action="<%=request.getContextPath()%>/updateReceivable"
							method="post">
								<input type="hidden" name="id" value="<%=receivable.getId()%>">
							</form>
							<form id="<%=deleteFormId%>" action="<%=request.getContextPath()%>/deleteReceivable"
							method="post">
								<input type="hidden" name="id" value="<%=receivable.getId()%>">
								<%=receivable.getId()%>
							</form>
						</td>
						
						<td>
							<input type="text" form="<%=updateFormId%>" name="customerName"
							 value="<%=receivable.getCustomerName()%>" class="editable" readonly required>
						</td>
						<td>
							<input type="text" form="<%=updateFormId%>" name="title" 
							value="<%=receivable.getTitle()%>" class="editable" readonly required>
						</td>
						<td>
							<input type="number" form="<%=updateFormId%>" name="amount" 
							value="<%=receivable.getAmount()%>" class="editable" readonly required>
						</td>
						<td>
							<input type="text" form="<%=updateFormId%>" name="expectedDate" 
							value="<%=receivable.getExpectedDate()%>" class="editable" readonly required>
						</td>
						<td>
							<input type="text" form="<%=updateFormId%>" name="status" 
							value="<%=receivable.getStatus()%>" class="editable" readonly required>
						</td>
						<td>
							<input type="text" form="<%=updateFormId%>" name="note" 
							value="<%=receivable.getNote()%>" class="editable" readonly required>
						</td>
						<td>
							<%=receivable.getCreatedAt()%>
						</td>
						<td>
							<%=receivable.getUpdated()%>
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
