package in.co.rays.project_3.controller;

import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.impl.SessionImpl;
import in.co.rays.project_3.util.HibDataSource;
import in.co.rays.project_3.util.JDBCDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@WebServlet(name = "JasperCtl", urlPatterns = { "/ctl/JasperCtl" })
public class JasperCtl extends BaseCtl {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        try {
            // Load the Jasper file as a resource stream from the classpath
            String resourcePath = "in/co/rays/project_3/jasperr/jdocker.jrxml";
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);

            if (inputStream == null) {
                System.out.println("Resource not found: " + resourcePath);
                return;
            }

            System.out.println("Resource found: " + resourcePath);

            // Compile the Jasper file
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            inputStream.close(); // Close the input stream after compiling

            // Prepare the parameters and database connection
            Map<String, Object> parameters = new HashMap<>();
            ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.project_3.bundle.system");
            String databaseType = rb.getString("DATABASE");

            if ("Hibernate".equalsIgnoreCase(databaseType)) {
                conn = ((SessionImpl) HibDataSource.getSession()).connection();
            } else if ("JDBC".equalsIgnoreCase(databaseType)) {
                conn = JDBCDataSource.getConnection();
            }

            // Fill the report with data from the database
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);

            // Export the filled report to a PDF and send it to the response
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
            response.setContentType("application/pdf");
            response.getOutputStream().write(pdf);
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Ensure the database connection is closed properly
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected String getView() {
        return null;
    }
}
