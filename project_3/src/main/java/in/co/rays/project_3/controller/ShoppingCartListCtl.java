package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ShoppingCartDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.ShoppingCartModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Servlet implementation class ShoppingCartListCtl
 */
@WebServlet(name = "ShoppingCartListCtl", urlPatterns = { "/ctl/ShoppingCartListCtl" })
public class ShoppingCartListCtl extends BaseCtl {
    private static final long serialVersionUID = 1L;

    @Override
    protected void preload(HttpServletRequest request) {
        // Implement preloading logic if needed
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        ShoppingCartDTO dto = new ShoppingCartDTO();
        dto.setProduct(DataUtility.getString(request.getParameter("product")));
        dto.setCategory(DataUtility.getString(request.getParameter("category")));
        populateBean(dto, request);
        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pageNo = 1;
        List next;
        
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
        pageNo = (pageNo > 0) ? pageNo : 1;
        pageSize = (pageSize > 0) ? pageSize : DataUtility.getInt(PropertyReader.getValue("page.size"));
        ShoppingCartDTO dto = (ShoppingCartDTO) populateDTO(request);
        ShoppingCartModelInt model = ModelFactory.getInstance().getShoppingCartModel();
        List list = null;
        try {
            list = model.search(dto, pageNo, pageSize);
            next = model.search(dto, pageNo + 1, pageSize);
            if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
            if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);

			} else {
				request.setAttribute("nextListSize", next.size());
			}
            ServletUtility.setDto(dto, request);
            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);
        } catch (ApplicationException e) {
            e.printStackTrace();
            ServletUtility.handleException(e, request, response);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List list = null;
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
        ShoppingCartDTO dto = (ShoppingCartDTO) populateDTO(request);
        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");
        ShoppingCartModelInt model = ModelFactory.getInstance().getShoppingCartModel();
        try {
            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {
                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }
            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;
                if (ids != null && ids.length > 0) {
                    ShoppingCartDTO deletedto = new ShoppingCartDTO();
                    for (String id : ids) {
                        deletedto.setId(DataUtility.getLong(id));
                        model.delete(deletedto);
                        ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
                    }
                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }
            } else if (OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.SHOPPING_CART_LIST_CTL, request, response);
                return;
            }
            if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.SHOPPING_CART_LIST_CTL, request, response);
                return;
            }
            dto = (ShoppingCartDTO) populateDTO(request);
            list = model.search(dto, pageNo, pageSize);
            ServletUtility.setDto(dto, request);
            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);
        } catch (ApplicationException e) {
            e.printStackTrace();
            ServletUtility.handleException(e, request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getView() {
        return ORSView.SHOPPING_CART_LIST_VIEW;
    }

}
