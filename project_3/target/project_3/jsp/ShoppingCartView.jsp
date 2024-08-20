<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.controller.ShoppingCartCtl"%>
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
<title>Shopping Cart view</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">
i.css {
    border: 2px solid #8080803b;
    padding-left: 10px;
    padding-bottom: 11px;
    background-color: #ebebe0;
}

.input-group-addon {
    box-shadow: 9px 8px 7px #001a33;
}

.hm {
    background-image: url('<%=ORSView.APP_CONTEXT%>/img/shopping_cart.jpg');
    background-size: 100%;
    padding-top: 8%;
    background-attachment: fixed;
}
</style>

</head>
<body class="hm">
    <div class="header">
        <%@include file="Header.jsp"%>
        <%@include file="calendar.jsp"%>
    </div>
    <div>

        <main>
        <form action="<%=ORSView.SHOPPING_CART_CTL%>" method="post">
            <jsp:useBean id="dto" class="in.co.rays.project_3.dto.ShoppingCartDTO"
                scope="request"></jsp:useBean>
            <div class="row pt-3">
                <!-- Grid column -->
                <div class="col-md-4 mb-4"></div>
                <div class="col-md-4 mb-4">
                    <div class="card input-group-addon">
                        <div class="card-body">

                            <%
                                long id = DataUtility.getLong(request.getParameter("id"));

                                if (dto.getId() != null && id > 0) {
                            %>
                            <h3 class="text-center default-text text-primary">Update Shopping Cart</h3>
                            <%
                                } else {
                            %>
                            <h3 class="text-center default-text text-primary">Add to Shopping Cart</h3>
                            <%
                                }
                            %>
                            <!--Body-->
                            <div>
                                <H4 align="center">
                                    <%
                                        if (!ServletUtility.getSuccessMessage(request).equals("")) {
                                    %>
                                    <div class="alert alert-success alert-dismissible">
                                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                                        <%=ServletUtility.getSuccessMessage(request)%>
                                    </div>
                                    <%
                                        }
                                    %>
                                </H4>

                                <H4 align="center">
                                    <%
                                        if (!ServletUtility.getErrorMessage(request).equals("")) {
                                    %>
                                    <div class="alert alert-danger alert-dismissible">
                                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                                        <%=ServletUtility.getErrorMessage(request)%>
                                    </div>
                                    <%
                                        }
                                    %>

                                </H4>

                                <input type="hidden" name="id" value="<%=dto.getId()%>">
                                <input type="hidden" name="createdBy"
                                    value="<%=dto.getCreatedBy()%>"> <input type="hidden"
                                    name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
                                    type="hidden" name="createdDatetime"
                                    value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
                                <input type="hidden" name="modifiedDatetime"
                                    value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">
                            </div>

                            <div class="md-form">

                                <span class="pl-sm-5"><b>Name</b> <span
                                    style="color: red;">*</span></span> </br>
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">
                                                <i class="fa fa-shopping-cart grey-text" style="font-size: 1rem;"></i>
                                            </div>
                                        </div>
                                        <input type="text" class="form-control" name="name"
                                            placeholder="Name"
                                            value="<%=DataUtility.getStringData(dto.getName())%>">
                                    </div>
                                </div>
                                <font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("name", request)%></font></br>

                                <span class="pl-sm-5"><b>Product</b> <span
                                    style="color: red;">*</span></span> </br>
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">
                                                <i class="fa fa-cube grey-text" style="font-size: 1rem;"></i>
                                            </div>
                                        </div>
                                        <input type="text" class="form-control" name="product"
                                            placeholder="Product"
                                            value="<%=DataUtility.getStringData(dto.getProduct())%>">
                                    </div>
                                </div>
                                <font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("product", request)%></font></br>

                                <span class="pl-sm-5"><b>Product Date</b> <span
                                    style="color: red;">*</span></span> </br>
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">
                                                <i class="fa fa-calendar grey-text" style="font-size: 1rem;"></i>
                                            </div>
                                        </div>
                                        <input type="date" class="form-control" name="productDate"
                                            placeholder="Product Date"
                                            value="<%=DataUtility.getDateString(dto.getProductDate())%>">
                                    </div>
                                </div>
                                <font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("productDate", request)%></font></br>

                                <!-- Existing fields: Quantity, Price, Category -->

                                <span class="pl-sm-5"><b>Quantity</b> <span
                                    style="color: red;">*</span></span></br>
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">
                                                <i class="fa fa-boxes grey-text" style="font-size: 1rem;"></i>
                                            </div>
                                        </div>
                                        <input type="number" class="form-control" name="quantity"
                                            placeholder="Quantity"
                                            value="<%=DataUtility.getStringData(dto.getQuantity()).equals("0")
                    ? ""
                    : DataUtility.getStringData(dto.getQuantity())%>"
                                            min="0" max="9999" oninput="validateQuantityInput(this)">
                                        <script>
                                            function validateQuantityInput(
                                                    input) {
                                                // Remove any non-numeric characters
                                                input.value = input.value
                                                        .replace(/[^0-9]/g, '');

                                                // Limit to four digits
                                                if (input.value.length > 4) {
                                                    input.value = input.value
                                                            .slice(0, 4);
                                                }
                                            }
                                        </script>
                                    </div>
                                </div>
                                <font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("quantity", request)%></font></br>

                                

                                <span class="pl-sm-5"><b>Category</b><span
                                    style="color: red;">*</span></span> </br>
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">
                                                <i class="fa fa-tags grey-text"
                                                    style="font-size: 1rem;"></i>
                                            </div>
                                        </div>
                                        <%
                                            /* HashMap categoryMap = new HashMap();
                                            categoryMap.put("Electronics", "Electronics");
                                            categoryMap.put("Fashion", "Fashion");
                                            categoryMap.put("Books", "Books");
                                            categoryMap.put("Home & Furniture", "Home & Furniture"); */

                                            HashMap categoryMap = new HashMap();
                                            categoryMap = (HashMap) request.getAttribute("categoryMap");
                                            String categoryList = HTMLUtility.getList("category", dto.getCategory(), categoryMap);
                                        %>
                                        <%=categoryList%>
                                    </div>
                                </div>
                                <font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("category", request)%></font></br>

                                <%
                                    if (dto.getId() != null && id > 0) {
                                %>

                                <div class="text-center">

                                    <input type="submit" name="operation"
                                        class="btn btn-success btn-md" style="font-size: 17px"
                                        value="<%=ShoppingCartCtl.OP_UPDATE%>"> <input type="submit"
                                        name="operation" class="btn btn-warning btn-md"
                                        style="font-size: 17px" value="<%=ShoppingCartCtl.OP_CANCEL%>">

                                </div>
                                <%
                                    } else {
                                %>
                                <div class="text-center">

                                    <input type="submit" name="operation"
                                        class="btn btn-success btn-md" style="font-size: 17px"
                                        value="<%=ShoppingCartCtl.OP_SAVE%>"> <input type="submit"
                                        name="operation" class="btn btn-warning btn-md"
                                        style="font-size: 17px" value="<%=ShoppingCartCtl.OP_RESET%>">
                                </div>

                            </div>
                            <%
                                }
                            %>
                        </div>
                    </div>
                </div>
        </form>
        </main>
        <div class="col-md-4 mb-4"></div>

    </div>

</body>
<%@include file="FooterView.jsp"%>

</body>
</html>