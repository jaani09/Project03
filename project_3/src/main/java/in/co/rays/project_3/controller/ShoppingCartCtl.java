package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import in.co.rays.project_3.dto.ShoppingCartDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ShoppingCartModelHibImp;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "ShoppingCartCtl", urlPatterns = { "/ctl/ShoppingCartCtl" })
public class ShoppingCartCtl extends BaseCtl {
    private static final long serialVersionUID = 1L;

    @Override
    protected void preload(HttpServletRequest request) {
        ShoppingCartModelHibImp model = new ShoppingCartModelHibImp();
        
        HashMap categoryMap = new HashMap();
        categoryMap.put("electronics", "electronics");
        categoryMap.put("furniture", "furniture");
        categoryMap.put("hardware", "hardware");
		request.setAttribute("categoryMap", categoryMap);
        
        try {
            List list = model.list();
            request.setAttribute("shoppingCartList", list);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;

        if (DataUtility.getString(request.getParameter("name")).equals("")) {
            request.setAttribute("name", "Name is required");
            pass = false;
        }
        if (DataUtility.getString(request.getParameter("product")).equals("")) {
            request.setAttribute("product", "Product is required");
            pass = false;
        }
        if (DataUtility.getString(request.getParameter("category")).equals("")) {
            request.setAttribute("category", "Category is required");
            pass = false;
        }
        if (DataUtility.getString(request.getParameter("quantity")).equals("")) {
            request.setAttribute("quantity", "Quantity is required");
            pass = false;
        } else if (!DataUtility.getString(request.getParameter("quantity")).matches("^[0-9]+$")) {
            request.setAttribute("quantity", "Invalid Quantity");
            pass = false;
        }

        return pass;
    }

    @Override
    protected ShoppingCartDTO populateDTO(HttpServletRequest request) {
        ShoppingCartDTO dto = new ShoppingCartDTO();
        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setName(DataUtility.getString(request.getParameter("name")));
        dto.setProduct(DataUtility.getString(request.getParameter("product")));
        dto.setCategory(DataUtility.getString(request.getParameter("category")));
        dto.setQuantity(DataUtility.getInt(request.getParameter("quantity")));

        populateBean(dto, request);
        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = DataUtility.getString(request.getParameter("operation"));
        long id = DataUtility.getLong(request.getParameter("id"));
        ShoppingCartModelHibImp model = new ShoppingCartModelHibImp();
        if (id > 0 || op != null) {
            ShoppingCartDTO dto;
            try {
                dto = model.findByPK(id);
                ServletUtility.setDto(dto, request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = DataUtility.getString(request.getParameter("operation"));
        ShoppingCartDTO dto = (ShoppingCartDTO) populateDTO(request);
        ShoppingCartModelHibImp model = new ShoppingCartModelHibImp();
        long id = DataUtility.getLong(request.getParameter("id"));
        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
            try {
                if (id > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Data is successfully updated", request);
                } else {
                    long pk = model.add(dto);
                    ServletUtility.setSuccessMessage("Data is successfully saved", request);
                }
                ServletUtility.setDto(dto, request);
            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                e.printStackTrace();
                return;
            } catch (DuplicateRecordException e) {
                e.printStackTrace();
            }
        } else if (OP_DELETE.equalsIgnoreCase(op)) {
            try {
                model.delete(dto);
                ServletUtility.redirect(ORSView.SHOPPING_CART_CTL, request, response);
                return;
            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                e.printStackTrace();
                return;
            }
        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.SHOPPING_CART_LIST_CTL, request, response);
            return;
        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.SHOPPING_CART_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.SHOPPING_CART_VIEW;
    }
}
