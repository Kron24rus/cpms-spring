package com.fireway.cpms.util;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ResponseWrapper {
    private HttpServletResponse response;

    public ResponseWrapper(HttpServletResponse response) {
        this.response = response;
    }

    public HttpServletResponse getInnerResponse() {
        return response;
    }

    public void writeString(String string) throws IOException{
        response.getWriter().write(string);
    }

    public void writeJson(Object object) throws IOException {
        response.getWriter().write(new Gson().toJson(object));
    }

    public void setError(int code, Exception e) throws IOException {
        response.setStatus(code);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        writeString(stringWriter.toString());
    }

    public void setForbiddenError(Exception e) throws IOException {
        setError(HttpServletResponse.SC_FORBIDDEN, e);
    }

    public void setNotFoundError(Exception e) throws IOException {
        setError(HttpServletResponse.SC_NOT_FOUND, e);
    }

    public void setNotImplementedError(Exception e) throws IOException {
        setError(HttpServletResponse.SC_NOT_IMPLEMENTED, e);
    }

    public void setInternalError(Exception e) throws IOException {
        setError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
    }

    public void setBadRequestError(Exception e) throws IOException {
        setError(HttpServletResponse.SC_BAD_REQUEST, e);
    }

    public void setNoContentStatus() {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
