package com.fireway.cpms.service;

import com.fireway.cpms.dao.impl.ProjectDaoImpl;
import com.fireway.cpms.dao.util.HibernateUtil;
import com.fireway.cpms.exception.*;
import com.fireway.cpms.util.RequestWrapper;
import com.fireway.cpms.util.ResponseWrapper;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.query.JRHibernateQueryExecuterFactory;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "report", urlPatterns = "/report")
public class ReportServlet extends GenericServlet {
    private static ProjectDaoImpl projectDao = new ProjectDaoImpl();
    @Override
    protected void handleGet(RequestWrapper request, ResponseWrapper response) throws ServletException, IOException, BadRequestException, DataAccessException, ForbiddenException, NotFoundException, NotImplementedException {
        Integer projectId = request.requirePositiveParameterInteger("id");

        //if (request.isUserAdmin() || projectDao.isMember(request.getCurrentUserId(), projectId)) {
            InputStream reportStream = getClass().getResourceAsStream("/report.jrxml");
            JasperReport jasperReport = null;
            try {
                jasperReport = JasperCompileManager.compileReport(reportStream);
                HibernateUtil.beginTransaction();
                Session session = HibernateUtil.getSession();

                Map params = new HashMap();
                params.put(JRHibernateQueryExecuterFactory.PARAMETER_HIBERNATE_SESSION, session);
                params.put("projectId", projectId);

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params);

                response.getInnerResponse().setHeader("Content-Disposition", "attachment; filename=\"JReport.pdf\"");
                response.getInnerResponse().setContentType("application/octet-stream");
                JasperExportManager.exportReportToPdfStream(jasperPrint, response.getInnerResponse().getOutputStream());
            } catch (JRException e) {
                e.printStackTrace();
                throw new DataAccessException(e.getMessage());
            } catch (HibernateException e) {
                HibernateUtil.rollback();
                e.printStackTrace();
                throw new DataAccessException(e.getMessage());
            } finally {
                HibernateUtil.closeSession();
            }
        /*} else {
            throw new ForbiddenException("Not a project member");
        }*/
    }
}
