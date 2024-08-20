package in.co.rays.project_3.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.JobDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.JobModelHibImp;
import in.co.rays.project_3.model.JobModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Servlet implementation class JobCtl
 */
@WebServlet(name = "JobCtl", urlPatterns = { "/ctl/JobCtl" })
public class JobCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	@Override
	protected void preload(HttpServletRequest request) {
		JobModelHibImp model = new JobModelHibImp();
		
		HashMap statusMap = new HashMap();
		statusMap.put("Open", "Open");
		statusMap.put("Close", "Close");
		request.setAttribute("statusMap", statusMap);
		try {
			List list = model.list();
			request.setAttribute("jobList", list);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataUtility.getString(request.getParameter("title")).equals("")) {
			request.setAttribute("title", "Title is Required");
			pass = false;
		} else if (!DataUtility.getString(request.getParameter("title")).matches("^[a-zA-Z ]+$")) {
			request.setAttribute("title", "Invalid Title");
			pass = false;
		}
		if (DataUtility.getString(request.getParameter("opening")).equals("")) {
			request.setAttribute("opening", "opening is Required");
			pass = false;
		}
		if (DataUtility.getString(request.getParameter("status")).equals("")) {
			request.setAttribute("status", "status is Required");
			pass = false;
		}
		if (DataUtility.getString(request.getParameter("experience")).equals("")) {
			request.setAttribute("experience", "experience is Required");
			pass = false;
		}

		return pass;
	}

	@Override
	protected JobDTO populateDTO(HttpServletRequest request) {
		JobDTO dto = new JobDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
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
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		JobModelHibImp model = new JobModelHibImp();
		if (id > 0 || op != null) {
			JobDTO dto;
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		JobDTO dto = (JobDTO) populateDTO(request);
		JobModelHibImp model = new JobModelHibImp();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
				} else {
					long pk = model.add(dto);
					ServletUtility.setSuccessMessage("Data is Successfully saved", request);
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
				ServletUtility.redirect(ORSView.JOB_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				e.printStackTrace();
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.JOB_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.JOB_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.JOB_VIEW;
	}
}
