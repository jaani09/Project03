
package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.EmployeeDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

import in.co.rays.project_3.model.EmployeeModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.RoleModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "EmployeeCtl", urlPatterns = { "/ctl/EmployeeCtl" })
public class EmployeeCtl extends BaseCtl {

	protected void preload(HttpServletRequest request) {
		HashMap map = new HashMap();
		map.put("HR", "HR");
		map.put("Finance", "Finance");
		map.put("Testing", "Testing");
		map.put("IT", "IT");
		request.setAttribute("departmentp", map);
	}

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", " name"));
			pass = false;
		}

		else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", " Name must contains alphabets only");
			System.out.println(pass);
			pass = false;
		}
		
		else if (request.getParameter("name").length() > 100) {
	        request.setAttribute("name", "Only 100 characters are allowed.");
	        pass = false;
	    }

		if (DataValidator.isNull(request.getParameter("department"))) {
			request.setAttribute("department", PropertyReader.getValue("error.require", "department"));
			System.out.println(pass);
			pass = false;
		}
		

		if (DataValidator.isNull(request.getParameter("dateOfJoining"))) {
			request.setAttribute("dateOfJoining", PropertyReader.getValue("error.require", "dateOfJoining"));
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("lastEmployeeName"))) {
			request.setAttribute("lastEmployeeName", PropertyReader.getValue("error.require", "lastEmployeeName"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("lastEmployeeName"))) {
			request.setAttribute("lastEmployeeName", " lastEmployeeName must contains alphabets only");
			System.out.println(pass);
			pass = false;
		}
		
		else if (request.getParameter("lastEmployeeName").length() > 100) {
	        request.setAttribute("lastEmployeeName", "Only 100 characters are allowed.");
	        pass = false;
	    }
		
		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		EmployeeDTO dto = new EmployeeDTO();

		System.out.println(request.getParameter("dob"));

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setDepartment(DataUtility.getString(request.getParameter("department")));
		dto.setLastEmployeeName(DataUtility.getString(request.getParameter("lastEmployeeName")));

		dto.setDateOfJoining(DataUtility.getDate(request.getParameter("dateOfJoining")));

		populateBean(dto, request);

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		EmployeeModelInt model = ModelFactory.getInstance().getEmployeeModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			EmployeeDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		EmployeeModelInt model = ModelFactory.getInstance().getEmployeeModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			EmployeeDTO dto = (EmployeeDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);

					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {

					try {
						model.add(dto);
						ServletUtility.setDto(dto, request);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (ApplicationException e) {
						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Login id already exists", request);
					}

				}
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			EmployeeDTO dto = (EmployeeDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.EMPLOYEE_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EMPLOYEE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EMPLOYEE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.EMPLOYEE_VIEW;
	}

}
