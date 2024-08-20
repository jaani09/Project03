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
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.ProductModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Product List functionality controller to perform Search and List operation.
 */
@WebServlet(name = "ProductListCtl", urlPatterns = { "/ctl/ProductListCtl" })
public class ProductListCtl extends BaseCtl {

    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(ProductListCtl.class);

    @Override
    protected void preload(HttpServletRequest request) {
        // Preload any data required for the form, e.g., dropdowns
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        ProductDTO dto = new ProductDTO();
        dto.setName(DataUtility.getString(request.getParameter("name")));
        dto.setCategory(DataUtility.getString(request.getParameter("category")));
        // Populate other fields as needed
        populateBean(dto, request);
        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("ProductListCtl doGet Start");
        List<ProductDTO> list;
        List<ProductDTO> next;
        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
        
        ProductDTO dto = (ProductDTO) populateDTO(request);
        ProductModelInt model = ModelFactory.getInstance().getProductModel();
        
        try {
            list = model.search(dto, pageNo, pageSize);
            next = model.search(dto, pageNo + 1, pageSize);
            
            ServletUtility.setList(list, request);
            
            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No records found", request);
            }
            
            if (next == null || next.size() == 0) {
                request.setAttribute("nextListSize", 0);
            } else {
                request.setAttribute("nextListSize", next.size());
            }
            
            ServletUtility.setDto(dto, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);
            
        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }
        log.debug("ProductListCtl doGet End");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("ProductListCtl doPost Start");
        List<ProductDTO> list;
        List<ProductDTO> next;
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
        
        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
        
        ProductDTO dto = (ProductDTO) populateDTO(request);
        String op = DataUtility.getString(request.getParameter("operation"));
        ProductModelInt model = ModelFactory.getInstance().getProductModel();
        
        try {
            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {
                
                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }
                
            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.PRODUCT_LIST_CTL, request, response);
                return;
            }
            
            list = model.search(dto, pageNo, pageSize);
            next = model.search(dto, pageNo + 1, pageSize);
            
            ServletUtility.setDto(dto, request);
            ServletUtility.setList(list, request);
            
            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No records found", request);
            }
            
            if (next == null || next.size() == 0) {
                request.setAttribute("nextListSize", 0);
            } else {
                request.setAttribute("nextListSize", next.size());
            }
            
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);
            
        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }
        log.debug("ProductListCtl doPost End");
    }

    @Override
    protected String getView() {
        return ORSView.PRODUCT_LIST_VIEW;
    }

}
