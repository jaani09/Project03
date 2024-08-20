package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.JobDTO;
import in.co.rays.project_3.dto.UserDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.JobModelHibImp;
import in.co.rays.project_3.model.JobModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Servlet implementation class JobListCtl
 */
@WebServlet(name = "JobListCtl", urlPatterns = { "/ctl/JobListCtl" })
public class JobListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	@Override
	protected void preload(HttpServletRequest request) {
		JobModelHibImp model = new JobModelHibImp();
		try {
			List list = model.list();
			request.setAttribute("jobList", list);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
	    JobDTO dto = new JobDTO();
	    dto.setTitle(DataUtility.getString(request.getParameter("title")));
	    dto.setOpening(DataUtility.getDate(request.getParameter("opening")));
	    dto.setExperience(DataUtility.getInt(request.getParameter("experience")));
	    dto.setStatus(DataUtility.getString(request.getParameter("status")));
	    populateBean(dto, request);
	    return dto;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageNo = 1;
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo > 0) ? pageNo : 1;
		List next;
		pageSize = (pageSize > 0) ? pageSize : DataUtility.getInt(PropertyReader.getValue("page.size"));
		JobDTO dto = (JobDTO) populateDTO(request);
		JobModelHibImp model = new JobModelHibImp();
		List list = null;
		try {
			list = model.search(dto, pageNo, pageSize);
			next = model.search(dto, pageNo + 1, pageSize);
			ServletUtility.setDto(dto, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);

			} else {
				request.setAttribute("nextListSize", next.size());
			}
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
	    //log.debug("JobListCtl doPost Start");
	    List list = null;
	    List next = null;
	    int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
	    int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

	    pageNo = (pageNo == 0) ? 1 : pageNo;
	    pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
	    JobDTO dto = (JobDTO) populateDTO(request);
	    String op = DataUtility.getString(request.getParameter("operation"));
	    System.out.println("op---->" + op);
	    String[] ids = request.getParameterValues("ids");
	    JobModelInt model = ModelFactory.getInstance().getJobModel();
	    try {

	        if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

	            if (OP_SEARCH.equalsIgnoreCase(op)) {
	            	if(dto==null) {
	            		return;
	            	}
	                pageNo = 1;
	            } else if (OP_NEXT.equalsIgnoreCase(op)) {
	                pageNo++;
	            } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
	                pageNo--;
	            }

	        } else if (OP_NEW.equalsIgnoreCase(op)) {
	            ServletUtility.redirect(ORSView.JOB_CTL, request, response);
	            return;
	        } else if (OP_RESET.equalsIgnoreCase(op)) {
	            ServletUtility.redirect(ORSView.JOB_LIST_CTL, request, response);
	            return;
	        } else if (OP_DELETE.equalsIgnoreCase(op)) {
	            pageNo = 1;
	            if (ids != null && ids.length > 0) {
	                JobDTO deletedto = new JobDTO();
	                for (String id : ids) {
	                    deletedto.setId(DataUtility.getLong(id));
	                    model.delete(deletedto);
	                    ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
	                }
	            } else {
	                ServletUtility.setErrorMessage("Select at least one record", request);
	            }
	        }
	        if (OP_BACK.equalsIgnoreCase(op)) {
	            ServletUtility.redirect(ORSView.JOB_LIST_CTL, request, response);
	            return;
	        }
	        dto = (JobDTO) populateDTO(request);
	        System.out.println("JobDTO details: " + dto);

	        list = model.search(dto, pageNo, pageSize);

	        ServletUtility.setDto(dto, request);
	        next = model.search(dto, pageNo + 1, pageSize);

	        ServletUtility.setList(list, request);
	        if (list == null || list.size() == 0) {
	            if (!OP_DELETE.equalsIgnoreCase(op)) {
	                ServletUtility.setErrorMessage("No record found ", request);
	            }
	        }
	        if (next == null || next.size() == 0) {
	            request.setAttribute("nextListSize", 0);
	        } else {
	            request.setAttribute("nextListSize", next.size());
	        }
	        ServletUtility.setList(list, request);
	        ServletUtility.setPageNo(pageNo, request);
	        ServletUtility.setPageSize(pageSize, request);
	        ServletUtility.forward(getView(), request, response);

	    } catch (ApplicationException e) {
	        //log.error(e);
	        ServletUtility.handleException(e, request, response);
	        return;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    //log.debug("JobListCtl doPost End");
	}

	@Override
	protected String getView() {
	    return ORSView.JOB_LIST_VIEW;
	}

	
	
	
}
