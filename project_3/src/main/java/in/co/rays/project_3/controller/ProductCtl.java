package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ProductDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.ProductModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Product functionality controller to perform add, delete, update, and view
 * operations.
 * 
 */
@WebServlet(urlPatterns = { "/ctl/ProductCtl" })
public class ProductCtl extends BaseCtl {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(ProductCtl.class);

    protected void preload(HttpServletRequest request) {
        // You can preload any data required for the form, e.g., dropdowns
    }

    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;
        // Validation logic for fields goes here
        return pass;
    }

    protected BaseDTO populateDTO(HttpServletRequest request) {
        ProductDTO dto = new ProductDTO();
        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setName(DataUtility.getString(request.getParameter("name")));
        dto.setDescription(DataUtility.getString(request.getParameter("description")));
        dto.setPrice(DataUtility.getDouble(request.getParameter("price")));
        dto.setDateOfPurchase(DataUtility.getDate(request.getParameter("dateOfPurchase")));
        dto.setCategory(DataUtility.getString(request.getParameter("category")));
        // Populate other fields as needed
        populateBean(dto, request);
        return dto;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        log.debug("ProductCtl Method doGet Started");
        String op = DataUtility.getString(request.getParameter("operation"));
        ProductModelInt model = ModelFactory.getInstance().getProductModel();
        long id = DataUtility.getLong(request.getParameter("id"));
        if (id > 0 || op != null) {
            ProductDTO dto;
            try {
                dto = model.findByPK(id);
                ServletUtility.setDto(dto, request);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
        ServletUtility.forward(getView(), request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String op = DataUtility.getString(request.getParameter("operation"));
        ProductModelInt model = ModelFactory.getInstance().getProductModel();
        long id = DataUtility.getLong(request.getParameter("id"));
        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
            ProductDTO dto = (ProductDTO) populateDTO(request);
            try {
                if (id > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Data is successfully updated", request);
                } else {
                    model.add(dto);
                    ServletUtility.setDto(dto, request);
                    ServletUtility.setSuccessMessage("Data is successfully saved", request);
                }
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            } catch (DuplicateRecordException e) {
                ServletUtility.setDto(dto, request);
                ServletUtility.setErrorMessage("Product with this name already exists", request);
            }
        } else if (OP_DELETE.equalsIgnoreCase(op)) {
            ProductDTO dto = (ProductDTO) populateDTO(request);
            try {
                model.delete(dto);
                ServletUtility.redirect(ORSView.PRODUCT_LIST_CTL, request, response);
                return;
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.PRODUCT_LIST_CTL, request, response);
            return;
        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.PRODUCT_CTL, request, response);
            return;
        }
        ServletUtility.forward(getView(), request, response);
        log.debug("ProductCtl Method doPost Ended");
    }

    @Override
    protected String getView() {
        return ORSView.PRODUCT_VIEW;
    }

}
