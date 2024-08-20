<%@page import="in.co.rays.project_3.controller.OrderCtl"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">
.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
	background-image: linear-gradient(to bottom right, yellow, white);
	padding-bottom: 11px;
}

.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/download (3).jpeg');
	background-size: cover;
	padding-top: 6%;
}

.error {
	color: red;
}
</style>
<!-- Include jQuery for easier DOM manipulation -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- Include jQuery UI for the datepicker -->
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

</head>
<body class="hm">
	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>
	<div>
		<main>
		<form action="<%=ORSView.ORDER_CTL%>" method="post">
			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.OrderDTO"
				scope="request"></jsp:useBean>
			<div class="row pt-3">
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card input-group-addon">
						<div class="card-body">
							<%
								long id = DataUtility.getLong(request.getParameter("id"));
							%>
							<h3 class="text-center default-text text-primary">
								<%=dto.getId() != null && id > 0 ? "Update Order" : "Add Order"%>
							</h3>
							<div>
								<%
									HashMap map = (HashMap) request.getAttribute("productList");
								%>
								<H4 align="center">
									<%
										String successMessage = ServletUtility.getSuccessMessage(request);
										String errorMessage = ServletUtility.getErrorMessage(request);
										if (!successMessage.isEmpty()) {
									%>
									<div class="alert alert-success alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=successMessage%>
									</div>
									<%
										}
										if (!errorMessage.isEmpty()) {
									%>
									<div class="alert alert-danger alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=errorMessage%>
									</div>
									<%
										}
									%>
								</H4>
								<input type="hidden" name="id" value="<%=dto.getId()%>">
							</div>
							<div class="md-form">
								<!-- Quantity Field -->
								<span class="pl-sm-5"><b>Quantity</b> <span
									style="color: red;">*</span></span><br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-user-alt grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="quantity"
											id="quantity"
											value="<%=DataUtility.getStringData(dto.getQuantity()).equals("0")
					? ""
					: DataUtility.getStringData(dto.getQuantity())%>"
											placeholder="Enter quantity"
											oninput="handleIntegerInput(this, 'quantityError', 10)"
											onblur="validateIntegerInput(this, 'quantityError', 10)">
									</div>
								</div>
								<font class="error pl-sm-5" id="quantityError"><%=ServletUtility.getErrorMessage("quantity", request)%></font><br>
								<!-- Product Field -->
								<span class="pl-sm-5"><b>Product</b> <span
									style="color: red;">*</span></span><br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-user-circle grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<%=HTMLUtility.getList("product", dto.getProduct(), map)%>
									</div>
								</div>
								<font class="error pl-sm-5" id="productError"><%=ServletUtility.getErrorMessage("product", request)%></font><br>
								<!-- Date Field -->
								<span class="pl-sm-5"><b>Date</b> <span
									style="color: red;">*</span></span><br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-calendar grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" id="datepicker2V" name="date"
											class="form-control" placeholder="Enter date"
											readonly="readonly"
											value="<%=DataUtility.getDateString(dto.getDate())%>">
									</div>
								</div>
								<font class="error pl-sm-5" id="dateErrorV"><%=ServletUtility.getErrorMessage("date", request)%></font><br>
								<!-- Amount Field -->
								<span class="pl-sm-5"><b>Amount</b> <span
									style="color: red;">*</span></span><br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-user-alt grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="amount"
											id="amount" placeholder="Enter amount"
											value="<%=DataUtility.getStringData(dto.getAmount()).equals("0")
					? ""
					: DataUtility.getStringData(dto.getAmount())%>"
											oninput="handleIntegerInput(this, 'amountError', 10)"
											onblur="validateIntegerInput(this, 'amountError', 10)">
									</div>
								</div>
								<font class="error pl-sm-5" id="amountError"><%=ServletUtility.getErrorMessage("amount", request)%></font><br>
								<div class="text-center">
									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=dto.getId() != null && id > 0 ? OrderCtl.OP_UPDATE : OrderCtl.OP_SAVE%>">
									<input type="submit" name="operation"
										class="btn btn-warning btn-md" style="font-size: 17px"
										value="<%=dto.getId() != null && id > 0 ? OrderCtl.OP_CANCEL : OrderCtl.OP_RESET%>">
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>
		</form>
		</main>
	</div>
	<div class="footer">
		<%@include file="FooterView.jsp"%>
	</div>
	<script src="<%=ORSView.APP_CONTEXT%>/js/utilities.js"></script>
	<script type="text/javascript">
		var isRedirecting = false;
		// Detect page reload
		function detectRefresh() {
			// If navigation type is 1 (reload) or the event is persisted (back/forward navigation)
			if (performance.navigation.type === 1 || window.performance
					&& window.performance.navigation.type === 1) {
				redirectOnRefresh();
			}
		}
		// Function to redirect to a specific URL
		function redirectOnRefresh() {
			if (!isRedirecting) {
				isRedirecting = true; // Set flag to true
				window.location.href = "/project_3/ctl/OrderCtl"; // Replace with your desired URL
			}
		}
		// Handle page refresh and unload events
		window.onbeforeunload = function() {
			redirectOnRefresh();
		};
		// Handle pageshow event (includes reload and back/forward navigation)
		window.addEventListener('pageshow', function(event) {
			detectRefresh();
		});
		// Check if the page was reloaded
		window.onload = function() {
			detectRefresh();
		};
		//Function for date and preload
		setupDropdownListener('product', 'productError');
		document.addEventListener("DOMContentLoaded", function() {
			console.log('Document loaded');
			initializeDatePicker('datepicker2V', 'dateErrorV');
		});
	</script>
</body>
</html>
