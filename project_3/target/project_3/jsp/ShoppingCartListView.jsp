<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.dto.ShoppingCartDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_3.model.ModelFactory"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.ShoppingCartListCtl"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Shopping Cart List View</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
    src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
<style>
.hm {
    background-image: url('<%=ORSView.APP_CONTEXT%>/img/universe_01.jpg');
    background-size: 100%;
    background-attachment: fixed;
}

.p1 {
    padding: 4px;
    width: 200px;
    font-size: bold;
}

.text {
    text-align: center;
}

input.form-control, select.form-control {
    margin-bottom: 0 !important;
    display: inline-block;
}

.btn-md {
    margin-bottom: 0 !important;
}
</style>
<nav class="fixed-top"> <%@include file="Header.jsp"%></nav>
<br>
<br>
<br>
</head>
<body class="hm">

    <%@include file="calendar.jsp"%>


    <div>
        <form class="pb-5" action="<%=ORSView.SHOPPING_CART_LIST_VIEW%>" method="post">
            <jsp:useBean id="dto" class="in.co.rays.project_3.dto.ShoppingCartDTO"
                scope="request"></jsp:useBean>
            <%
                List list1 = (List) request.getAttribute("roleList");
            %>


            <%
                int pageNo = ServletUtility.getPageNo(request);
                int pageSize = ServletUtility.getPageSize(request);
                int index = ((pageNo - 1) * pageSize) + 1;
                int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
                /* RoleDTO rbean1 = new RoleDTO();
                RoleModelInt rmodel = ModelFactory.getInstance().getRoleModel(); */

                List list = ServletUtility.getList(request);

                Iterator<ShoppingCartDTO> it = list.iterator();
                if (list.size() != 0) {
            %>
            <center>
                <h1 class="text-light font-weight-bold pt-3">Shopping Cart List View</h1>
            </center>
            <div class="row">
                <div class="col-md-4"></div>
                <%
                    if (!ServletUtility.getSuccessMessage(request).equals("")) {
                %>

                <div class="col-md-4 alert alert-success alert-dismissible"
                    style="background-color: #80ff80">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <h4>
                        <font color="#008000"><%=ServletUtility.getSuccessMessage(request)%></font>
                    </h4>
                </div>
                <%
                    }
                %>
                <div class="col-md-4"></div>
            </div>
            <div class="row">
                <div class="col-md-4"></div>

                <%
                    if (!ServletUtility.getErrorMessage(request).equals("")) {
                %>
                <div class=" col-md-4 alert alert-danger alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <h4>
                        <font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
                    </h4>
                </div>
                <%
                    }
                %>
                <div class="col-md-4"></div>
            </div>

            <div class="row align-items-center">
                <div class="col-sm-1"></div>

                <div class="col-sm-2">
                    <input type="text" name="name" placeholder="Enter name"
                        class="form-control"
                        value="<%=ServletUtility.getParameter("name", request)%>"
                        maxlength="50">
                </div>
                &emsp;

                <!-- Product -->
                <div class="col-sm-2">
                    <input type="text" name="product" placeholder="Enter product"
                        class="form-control"
                        value="<%=ServletUtility.getParameter("product", request)%>"
                        maxlength="50">
                </div>
                &emsp;

                <!-- Quantity -->
                <div class="col-sm-2">
                    <input type="text" name="quantity" placeholder="Enter quantity"
                        class="form-control"
                        value="<%=ServletUtility.getParameter("quantity", request)%>"
                        maxlength="5">
                </div>
                &emsp;

                <!-- Product Date -->
                <div class="col-sm-2">
                    <input type="text" name="productDate" id="udate" readonly="readonly"
                        placeholder="Enter Date" class="form-control"
                        value="<%=ServletUtility.getParameter("productDate", request)%>">
                </div>
                &emsp;

                <!-- Category -->
                <div class="col-sm-2">
                    <input type="text" name="category" placeholder="Enter category"
                        class="form-control"
                        value="<%=ServletUtility.getParameter("category", request)%>"
                        maxlength="50">
                </div>
                &emsp;

                <div class="col-sm-2">
                    <input type="submit" class="btn btn-primary btn-md"
                        style="font-size: 15px" name="operation"
                        value="<%=ShoppingCartListCtl.OP_SEARCH%>"> &emsp; <input
                        type="submit" class="btn btn-dark btn-md" style="font-size: 15px"
                        name="operation" value="<%=ShoppingCartListCtl.OP_RESET%>">
                </div>
                <div class="col-sm-1"></div>
            </div>


            </br>
            <div style="margin-bottom: 20px;" class="table-responsive">
                <table class="table table-bordered table-dark table-hover">
                    <thead>
                        <tr style="background-color: #8C8C8C;">

                            <th width="10%"><input type="checkbox" id="select_all"
                                name="Select" class="text"> Select All</th>
                            <th width="5%" class="text">S.NO</th>
                            <th width="25%" class="text">Name</th>
                            <th width="20%" class="text">Product</th>
                            <th width="10%" class="text">Quantity</th>
                            <th width="20%" class="text">Product Date</th>
                            <th width="10%" class="text">Category</th>
                            <th width="5%" class="text">Edit</th>

                        </tr>
                    </thead>
                    <%
                        while (it.hasNext()) {
                                dto = it.next();

                                /* RoleDTO rbean = rmodel.findByPK(dto.getRoleId()); */
                    %>
                    <tbody>
                        <tr>
                            <td align="center"><input type="checkbox" class="checkbox"
                                name="ids" value="<%=dto.getId()%>"></td>
                            <td class="text"><%=index++%></td>
                            <td class="text"><%=dto.getName()%></td>
                            <td class="text"><%=dto.getProduct()%></td>
                            <td class="text"><%=dto.getQuantity()%></td>
                            <td class="text"><%=DataUtility.getDateString(dto.getProductDate())%></td>
                            <td class="text"><%=dto.getCategory()%></td>
                            <td class="text"><a href="ShoppingCartListViewCtl?id=<%=dto.getId()%>">Edit</a></td>
                        </tr>

                    </tbody>
                    <%
                        }
                    %>
                </table>
            </div>
            <table width="100%">
                <tr>
                    <td><input type="submit" name="operation"
                        class="btn btn-warning btn-md" style="font-size: 17px"
                        value="<%=ShoppingCartListCtl.OP_PREVIOUS%>"
                        <%=pageNo > 1 ? "" : "disabled"%>></td>
                    <td><input type="submit" name="operation"
                        class="btn btn-primary btn-md" style="font-size: 17px"
                        value="<%=ShoppingCartListCtl.OP_NEW%>"></td>
                    <td><input type="submit" name="operation"
                        class="btn btn-danger btn-md" style="font-size: 17px"
                        value="<%=ShoppingCartListCtl.OP_DELETE%>"></td>

                    <td align="right"><input type="submit" name="operation"
                        class="btn btn-warning btn-md" style="font-size: 17px"
                        style="padding: 5px;" value="<%=ShoppingCartListCtl.OP_NEXT%>"
                        <%=(nextPageSize != 0) ? "" : "disabled"%>></td>
                </tr>
                <tr></tr>
            </table>

            <%
                }
                if (list.size() == 0) {
            %>
            <center>
                <h1 style="font-size: 40px; color: white;">
                    <b>Shopping Cart List View</b>
                </h1>
            </center>
            </br>
            <div class="row">
                <div class="col-md-4"></div>

                <%
                    if (!ServletUtility.getErrorMessage(request).equals("")) {
                %>
                <div class=" col-md-4 alert alert-danger alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <h4>
                        <font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
                    </h4>
                </div>
                <%
                    }
                %>




                <%
                    if (!ServletUtility.getSuccessMessage(request).equals("")) {
                %>

                <div class="col-md-4 alert alert-success alert-dismissible"
                    style="background-color: #80ff80">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <h4>
                        <font color="#008000"><%=ServletUtility.getSuccessMessage(request)%></font>
                    </h4>
                </div>
                <%
                    }
                %>
                <div style="padding-left: 48%;">
                    <input type="submit" name="operation"
                        class="btn btn-primary btn-md" style="font-size: 17px"
                        value="<%=ShoppingCartListCtl.OP_BACK%>">
                </div>

                <div class="col-md-4"></div>
            </div>

            <%
                }
            %>

            <input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
                type="hidden" name="pageSize" value="<%=pageSize%>">
        </form>


    </div>


</body>
<%@include file="FooterView.jsp"%>
</html>
