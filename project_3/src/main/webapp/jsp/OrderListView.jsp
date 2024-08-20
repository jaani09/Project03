<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_3.dto.OrderDTO"%>
<%@page import="in.co.rays.project_3.controller.OrderListCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="java.util.HashMap"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Order List</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/utilities.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
<style>
.table-responsive {
	overflow-x: auto;
	max-height: 400px;
}

.row {
	display: flex;
	flex-wrap: wrap;
}

.align-items-start {
	align-items: flex-start;
}

.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/1234.jpeg');
	background-size: cover;
	padding-top: 6%;
}

.button-container {
	display: flex;
	align-items: center;
}

.error-container {
	display: flex;
	flex-direction: column;
	justify-content: flex-start;
}

.error-message {
	height: 1.5em;
	color: red;
	font-size: 1em;
	margin-top: 0.1em;
}

.btn-md {
	margin-right: 10px;
}

.text {
	text-align: center;
}
</style>
</head>

<body class="hm">
	<%@include file="Header.jsp"%>
	<%@include file="calendar.jsp"%>
	<jsp:useBean id="dto" class="in.co.rays.project_3.dto.OrderDTO"
		scope="request"></jsp:useBean>

	<form class="pb-1" action="<%=ORSView.ORDER_LIST_CTL%>" method="post">
		<center>
			<h1 class="text-secondary font-weight-bold pt-3">Order List</h1>
		</center>

		<%-- Success and Error Messages --%>
		<div class="row justify-content-center">
			<%
				String successMsg = ServletUtility.getSuccessMessage(request);
				String errorMsg = ServletUtility.getErrorMessage(request);
				if (!successMsg.isEmpty()) {
			%>
			<div class="col-md-4 alert alert-success alert-dismissible">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4 class="text-success"><%=successMsg%></h4>
			</div>
			<%
				} else if (!errorMsg.isEmpty()) {
			%>
			<div class="col-md-4 alert alert-danger alert-dismissible">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4 class="text-danger"><%=errorMsg%></h4>
			</div>
			<%
				}
			%>
		</div>
		<%
			HashMap<String, String> map = (HashMap<String, String>) request.getAttribute("productList");
		%>
		<%-- Form Inputs --%>
		<div class="row align-items-start" style="padding-left: 20px;">
			<div class="col-sm-1"></div>
			<%-- <div class="col-sm-2 error-container">
					<input type="text" class="form-control" name="productStr"
						value="<%=ServletUtility.getParameter("productStr", request)%>"
						placeholder="Enter product" id="productStr"
						oninput="handleLetterInput(this, 'productStrError', 30)"
						onblur="validateLetterInput(this, 'productStrError', 30)">
					<div id="productStrError" class="error-message"></div>
				</div> --%>
			<div class="col-sm-2 error-container">
				<input type="text" class="form-control" name="quantity"
					value="<%=ServletUtility.getParameter("quantity", request)%>"
					placeholder="Enter quantity" id="quantity"
					oninput="handleIntegerInput(this, 'quantityError', 10)"
					onblur="validateIntegerInput(this, 'quantityError', 10)">
				<div id="quantityError" class="error-message"></div>
			</div>

			<div class="col-sm-2">
				<div class="input-group">
					<%=HTMLUtility.getList("product", dto.getProduct(), map)%>
				</div>
			</div>
			<div class="col-sm-2">
				<input type="text" id="datepicker2" name="date" class="form-control"
					placeholder="Enter date" readonly="readonly"
					value="<%=DataUtility.getDateString(dto.getDate())%>">
			</div>
			<div class="col-sm-2 error-container">
				<input type="text" name="amount" placeholder="Enter Amount"
					class="form-control"
					value="<%=ServletUtility.getParameter("amount", request)%>"
					id="amount" oninput="handleIntegerInput(this, 'amountError', 10)"
					onblur="validateIntegerInput(this, 'amountError', 10)">
				<div id="amountError" class="error-message"></div>
			</div>
			<div class="col-sm-1 button-container d-flex align-items-center">
				<input type="submit" class="btn btn-primary btn-md"
					style="font-size: 15px" name="operation"
					value="<%=OrderListCtl.OP_SEARCH%>"> <input type="submit"
					class="btn btn-dark btn-md" style="font-size: 15px"
					name="operation" value="<%=OrderListCtl.OP_RESET%>">
			</div>
		</div>
		</div>

		<%-- Table --%>
		<div class="table-responsive mb-4">
			<table class="table table-bordered table-dark table-hover">
				<thead>
					<tr style="background-color: purple;">
						<th width="10%"><input type="checkbox" id="select_all"
							name="Select" class="text"> Select All</th>
						<th width="5%" class="text">S.NO</th>
						<th width="15%" class="text">Quantity</th>
						<th width="15%" class="text">Product</th>
						<th width="15%" class="text">Date</th>
						<th width="15%" class="text">Amount</th>
						<th width="10%" class="text">Edit</th>
					</tr>
				</thead>
				<tbody>
					<%
						List<OrderDTO> list = ServletUtility.getList(request);
						Iterator<OrderDTO> it = list.iterator();
						int index = ((ServletUtility.getPageNo(request) - 1) * ServletUtility.getPageSize(request)) + 1;
						while (it.hasNext()) {
							dto = it.next();
					%>
					<tr>
						<td align="center"><input type="checkbox" class="checkbox"
							name="ids" value="<%=dto.getId()%>"></td>
						<td class="text"><%=index++%></td>
						<td class="text"><%=dto.getQuantity()%></td>
						<td class="text"><%=dto.getProduct()%></td>
						<td class="text"><%=DataUtility.getDateString(dto.getDate())%></td>
						<td class="text"><%=dto.getAmount()%></td>
						<td class="text"><a href="OrderCtl?id=<%=dto.getId()%>">Edit</a></td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
		</div>

		<%-- Pagination and Actions --%>
		<div style="padding-bottom: 50px;">
			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						class="btn btn-warning btn-md" style="font-size: 17px"
						value="<%=OrderListCtl.OP_PREVIOUS%>"
						<%=ServletUtility.getPageNo(request) > 1 ? "" : "disabled"%>></td>
					<td><input type="submit" name="operation"
						class="btn btn-primary btn-md" style="font-size: 17px"
						value="<%=OrderListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation"
						class="btn btn-danger btn-md" style="font-size: 17px"
						value="<%=OrderListCtl.OP_DELETE%>"></td>
					<td align="right"><input type="submit" name="operation"
						class="btn btn-warning btn-md" style="font-size: 17px"
						value="<%=OrderListCtl.OP_NEXT%>"
						<%=DataUtility.getInt(request.getAttribute("nextListSize").toString()) != 0 ? "" : "disabled"%>></td>
				</tr>
			</table>
		</div>

		<input type="hidden" name="pageNo"
			value="<%=ServletUtility.getPageNo(request)%>"> <input
			type="hidden" name="pageSize"
			value="<%=ServletUtility.getPageSize(request)%>">
	</form>

	<%@include file="FooterView.jsp"%>
</body>
</html>
