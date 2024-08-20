<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="in.co.rays.project_3.dto.ProductDTO" %>
<%@ page import="in.co.rays.project_3.util.DataUtility" %>
<%@ page import="in.co.rays.project_3.util.HTMLUtility" %>
<%@ page import="in.co.rays.project_3.util.ServletUtility" %>
<%@ page import="in.co.rays.project_3.controller.ProductCtl" %>
<%@ page import="in.co.rays.project_3.controller.ORSView" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- CSS styles -->
<style type="text/css">
    /* Define your styles here */
</style>
</head>
<body>
    <div class="header">
        <%@ include file="Header.jsp" %>
        <%-- Include other headers or navigation if needed --%>
    </div>
    <div class="main-content">
        <form action="<%=ORSView.PRODUCT_CTL%>" method="post">
            <jsp:useBean id="dto" class="in.co.rays.project_3.dto.ProductDTO" scope="request"></jsp:useBean>
            <div class="container">
                <div class="row">
                    <div class="col-md-4"></div>
                    <div class="col-md-4">
                        <div class="card">
                            <div class="card-body">
                                <% if (dto.getId() != null && dto.getId() > 0) { %>
                                    <h3 class="text-center text-primary">Update Product</h3>
                                <% } else { %>
                                    <h3 class="text-center text-primary">Add Product</h3>
                                <% } %>

                                <!-- Success and Error Messages -->
                                <c:if test="${not empty requestScope.successMessage}">
                                    <div class="alert alert-success alert-dismissible">
                                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                                        <c:out value="${requestScope.successMessage}" escapeXml="false" />
                                    </div>
                                </c:if>
                                <c:if test="${not empty requestScope.errorMessage}">
                                    <div class="alert alert-danger alert-dismissible">
                                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                                        <c:out value="${requestScope.errorMessage}" escapeXml="false" />
                                    </div>
                                </c:if>

                                <!-- Hidden Fields -->
                                <input type="hidden" name="id" value="<%= dto.getId() %>">
                                <input type="hidden" name="createdBy" value="<%= dto.getCreatedBy() %>">
                                <input type="hidden" name="modifiedBy" value="<%= dto.getModifiedBy() %>">
                                <input type="hidden" name="createdDatetime" value="<%= dto.getCreatedDatetime() %>">
                                <input type="hidden" name="modifiedDatetime" value="<%= dto.getModifiedDatetime() %>">

                                <!-- Product Name -->
                                <div class="form-group">
                                    <label for="productName">Product Name <span style="color: red;">*</span></label>
                                    <input type="text" class="form-control" id="productName" name="productName" 
                                           placeholder="Product Name" value="<%= DataUtility.getString(dto.getName()) %>">
                                    <font color="red"><%= ServletUtility.getErrorMessage("productName", request) %></font>
                                </div>

                                <!-- Price -->
                                <div class="form-group">
                                    <label for="price">Price <span style="color: red;">*</span></label>
                                    <input type="text" class="form-control" id="price" name="price" 
                                           placeholder="Price" value="<%= dto.getPrice() %>">
                                    <font color="red"><%= ServletUtility.getErrorMessage("price", request) %></font>
                                </div>

                                <!-- Category -->
                                <div class="form-group">
                                    <label for="category">Category <span style="color: red;">*</span></label>
                                    <input type="text" class="form-control" id="category" name="category" 
                                           placeholder="Category" value="<%= DataUtility.getString(dto.getCategory()) %>">
                                    <font color="red"><%= ServletUtility.getErrorMessage("category", request) %></font>
                                </div>

                                <!-- Submit Button -->
                                <div class="text-center">
                                    <% if (dto.getId() != null && dto.getId() > 0) { %>
                                        <input type="submit" name="operation" class="btn btn-success" 
                                               value="<%= ProductCtl.OP_UPDATE %>">
                                        <input type="submit" name="operation" class="btn btn-warning" 
                                               value="<%= ProductCtl.OP_CANCEL %>">
                                    <% } else { %>
                                        <input type="submit" name="operation" class="btn btn-success" 
                                               value="<%= ProductCtl.OP_SAVE %>">
                                        <input type="submit" name="operation" class="btn btn-warning" 
                                               value="<%= ProductCtl.OP_RESET %>">
                                    <% } %>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="footer">
        <%@ include file="FooterView.jsp" %>
        <%-- Include other footer content if needed --%>
    </div>
</body>
</html>
