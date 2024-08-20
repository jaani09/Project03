package in.co.rays.project_3.model;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * ModelFactory decides which model implementation run
 * 
 * @author Suraj Sahu
 *
 */
public final class ModelFactory {

	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.project_3.bundle.system");
	private static final String DATABASE = rb.getString("DATABASE");
	private static ModelFactory mFactory = null;
	private static HashMap modelCache = new HashMap();

	
	private ModelFactory() {
 
	}

	public static ModelFactory getInstance() {
		if (mFactory == null) {
			mFactory = new ModelFactory();
		}
		return mFactory;
	}
	
	public OrderModelInt getOrderModel() {
		OrderModelInt orderModel = (OrderModelInt) modelCache.get("orderModel");
		if (orderModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				orderModel = new OrderModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				orderModel = new OrderModelHibImp();
			}
			modelCache.put("orderModel", orderModel);
		}

		return orderModel;
	}
	
	public EmployeeModelInt getEmployeeModel() {
		EmployeeModelInt employeeModel = (EmployeeModelInt) modelCache.get("employeeModel");
		if (employeeModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				employeeModel = new EmployeeModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				employeeModel = new EmployeeModelHibImp();
			}
			modelCache.put("employeeModel", employeeModel);
		}

		return employeeModel;
	}

	public ShoppingCartModelInt getShoppingCartModel() {
	    ShoppingCartModelInt shoppingCartModel = (ShoppingCartModelInt) modelCache.get("shoppingCartModel");
	    System.out.println(shoppingCartModel);
	    if (shoppingCartModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            shoppingCartModel = new ShoppingCartModelHibImp(); // Adjust with your Hibernate implementation class for ShoppingCartModel
	        } else if ("JDBC".equals(DATABASE)) {
	            /* shoppingCartModel = new ShoppingCartModelJDBCImpl(); */ // Adjust with your JDBC implementation class for ShoppingCartModel
	        }
	        System.out.println(DATABASE);
	        modelCache.put("shoppingCartModel", shoppingCartModel);
	    }

	    return shoppingCartModel;
	}

	
	public JobModelInt getJobModel() {
	    JobModelInt jobModel = (JobModelInt) modelCache.get("jobModel");
	    System.out.println(jobModel);
	    if (jobModel == null) {
	        if ("Hibernate".equals(DATABASE)) {
	            jobModel = new JobModelHibImp(); // Adjust with your Hibernate implementation class for JobModel
	        } else if ("JDBC".equals(DATABASE)) {
				/* jobModel = new JobModelJDBCImpl(); */// Adjust with your JDBC implementation class for JobModel
	        }
	        System.out.println(DATABASE);
	        modelCache.put("jobModel", jobModel);
	    }

	    return jobModel;
	}

	
	public ProductModelInt getProductModel() {
        ProductModelInt productModel = (ProductModelInt) modelCache.get("productModel");
        System.out.println(productModel);
        
        if (productModel == null) {
            if ("Hibernate".equals(DATABASE)) {
                productModel = new ProductModelHibImp();
			} /*
				 * else if ("JDBC".equals(DATABASE)) { productModel = new
				 * ProductModelJDBCImpl(); }
				 */
            
            // Cache the instance for future use
            modelCache.put("productModel", productModel);
        }

        return productModel;
    }
	
	
	
	
	public UserModelInt getUserModel() {
		//UserModelInt userModel = null;
		UserModelInt userModel = (UserModelInt) modelCache.get("userModel");
		System.out.println(userModel);
		if (userModel == null) {
			
			if ("Hibernate".equals(DATABASE)) {
				userModel = new UserModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				userModel = new UserModelJDBCImpl();
			}
			System.out.println(DATABASE);
			modelCache.put("userModel", userModel);
		}

		return userModel;
	}
	
	
	
	
	public MarksheetModelInt getMarksheetModel() {
		MarksheetModelInt marksheetModel = (MarksheetModelInt) modelCache.get("marksheetModel");
		if (marksheetModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				marksheetModel = new MarksheetModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				marksheetModel = new MarksheetModelJDBCImpl();
			}
			modelCache.put("marksheetModel", marksheetModel);
		}
		return marksheetModel;
	}

	public CollegeModelInt getCollegeModel() {
		CollegeModelInt collegeModel = (CollegeModelInt) modelCache.get("collegeModel");
		if (collegeModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				collegeModel = new CollegeModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				collegeModel = new CollegeModelJDBCImpl();
			}
			modelCache.put("collegeModel", collegeModel);
		}
		return collegeModel;
	}

	public RoleModelInt getRoleModel() {
		RoleModelInt roleModel = (RoleModelInt) modelCache.get("roleModel");
		if (roleModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				roleModel = new RoleModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				roleModel = new RoleModelJDBCImpl();
			}
			modelCache.put("roleModel", roleModel);
		}
		return roleModel;
	}

	public StudentModelInt getStudentModel() { 
		StudentModelInt studentModel = (StudentModelInt) modelCache.get("studentModel");
		if (studentModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				studentModel = new StudentModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				studentModel = new StudentModelJDBCImpl();
			}
			modelCache.put("studentModel", studentModel);
		}

		return studentModel;
	}

	public CourseModelInt getCourseModel() {
		CourseModelInt courseModel = (CourseModelInt) modelCache.get("courseModel");
		if (courseModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				courseModel = new CourseModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				courseModel = new CourseModelJDBCImpl();
			}
			modelCache.put("courseModel", courseModel);
		}

		return courseModel;
	}

	public TimetableModelInt getTimetableModel() {

		TimetableModelInt timetableModel = (TimetableModelInt) modelCache.get("timetableModel");

		if (timetableModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				timetableModel = new TimetableModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				timetableModel = new TimetableModelJDBCImpl();
			}
			modelCache.put("timetableModel", timetableModel);
		}

		return timetableModel;
	}

	public SubjectModelInt getSubjectModel() {
		SubjectModelInt subjectModel = (SubjectModelInt) modelCache.get("subjectModel");
		if (subjectModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				subjectModel = new SubjectModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				subjectModel = new SubjectModelJDBCImpl();
			}
			modelCache.put("subjectModel", subjectModel);
		} 

		return subjectModel;
	}

	public FacultyModelInt getFacultyModel() {
		FacultyModelInt facultyModel = (FacultyModelInt) modelCache.get("facultyModel");
		if (facultyModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				facultyModel = new FacultyModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				facultyModel = new FacultyModelJDBCImpl();
			}
			modelCache.put("facultyModel", facultyModel);
		}

		return facultyModel;
	}
	public TradeModelInt getTradeModel() {
		//UserModelInt userModel = null;
		TradeModelInt tradeModel = (TradeModelInt) modelCache.get("tradeModel");
		System.out.println(tradeModel);
		if (tradeModel == null) {
			
			if ("Hibernate".equals(DATABASE)) {
				tradeModel = new TradeModelHibImp();
			}
			System.out.println(DATABASE);
			modelCache.put("tradeModel", tradeModel);
		}

		return tradeModel;
	}
	public TransactionModelInt getTransactionModel() {

		TransactionModelInt transactionModel = (TransactionModelInt) modelCache.get("transactionModel");
		if (transactionModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				transactionModel = new TransactionModelHibImp();
			}
			
			modelCache.put("transactionModel", transactionModel);
		}

		return transactionModel;
	}


}
