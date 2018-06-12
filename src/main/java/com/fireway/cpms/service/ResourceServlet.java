package com.fireway.cpms.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "resource", urlPatterns = "/resources/*")
public class ResourceServlet extends HttpServlet {
    private static String PATH = "/usr/ui5/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String context = req.getContextPath();
        String uri = req.getRequestURI();

        String path = StringUtils.replaceFirst(uri, context, "");
        File file = new File(PATH + path);
        if (!file.exists() && !file.isFile()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (StringUtils.endsWith(path, ".js")) {
            resp.setContentType("application/javascript");
        } else if (StringUtils.endsWith(path, ".css")) {
            resp.setContentType("text/css");
        } else {
            resp.setContentType("text/plain");
        }
        FileUtils.copyFile(file, resp.getOutputStream());
    }
}
