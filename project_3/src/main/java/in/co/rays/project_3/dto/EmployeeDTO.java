
package in.co.rays.project_3.dto;

import java.util.Date;

public class EmployeeDTO extends BaseDTO{
	
	private String name;
	private String department;
	private Date dateOfJoining ;
	private String lastEmployeeName;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Date getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public String getLastEmployeeName() {
		return lastEmployeeName;
	}
	public void setLastEmployeeName(String lastEmployeeName) {
		this.lastEmployeeName = lastEmployeeName;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return department+"";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return department+"";
	}
	
	
	
	
}