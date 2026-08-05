<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="receivable.Receivable" %>
<!DOCTYPE html>
<html lang="zh-Hant" dir="ltr">
<head>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
	<meta charset="utf-8">
	<title>應收款資料表</title>
</head>
<body>
	<h1>應收款資料表</h1>
	<div class="page-actions">
    	<a class="secondary" href="<%= request.getContextPath() %>/">回主控畫面</a>
    	<a href="<%= request.getContextPath() %>/addReceivable">新增應收款</a>
	</div>
	<p>
  		
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
					
					 String statusText = "PAID".equals(receivable.getStatus()) ? "已付款" : "未付款";
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
							value="<%=receivable.getAmount()%>" class="editable" step="0.01" readonly required>
						</td>
						<td>
							<input type="date" form="<%=updateFormId%>" name="expectedDate" 
							value="<%=receivable.getExpectedDate()%>" class="editable" readonly required>
						</td>
						<td>
						    <span class="status-text">
						        <%= statusText %>
						    </span>
						
						    <span class="status-radio" style="display:none;">
						        <label>
						            <input form="<%= updateFormId %>" type="radio"
						                   name="status" value="UNRECEIVED"
						                   <%= "UNRECEIVED".equals(receivable.getStatus()) ? "checked" : "" %>>
						            未收款
						        </label>
						
						        <label>
						            <input form="<%= updateFormId %>" type="radio"
						                   name="status" value="RECEIVED"
						                   <%= "RECEIVED".equals(receivable.getStatus()) ? "checked" : "" %>>
						            已收款
						        </label>
						    </span>
						</td>
						<td>
							<input type="text" form="<%=updateFormId%>" name="note" 
							value="<%=receivable.getNote()%>" class="editable" readonly>
						</td>
						<td>
							<%=receivable.getCreatedAt()%>
						</td>
						<td>
							<%=receivable.getUpdatedAt()%>
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

                    const row = button.closest("tr");
                    const statusText = row.querySelector(".status-text");
                    const statusRadio = row.querySelector(".status-radio");

                    statusText.style.display = "none";
                    statusRadio.style.display = "inline";

                    button.textContent = "更新";
                    button.dataset.mode = "update";
                }else {
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
                const result = confirm("確定要刪除這筆應收款資料嗎？");

                if (!result) {
                    event.preventDefault();
                }
            });
        });
    </script>
  </body>
</html>
