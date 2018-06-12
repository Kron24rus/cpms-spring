package com.fireway.cpms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LoggerWrapper {

    private Logger logger;

    public LoggerWrapper(Logger logger) {
        this.logger = logger;
    }

    private static String getExceptionInfo(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    private static String getThrowableInfo(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    public static LoggerWrapper getLogger(Class aClass) {
        return new LoggerWrapper((LoggerFactory.getLogger(aClass)));
    }

    public void debug(String msg) {
        logger.debug(msg);
    }

    public void info(String msg) {
        logger.info(msg);
    }

    public void info(String msg, Object... arguments) {
        logger.info(msg, arguments);
    }

    public void warn(String msg) {
        logger.warn(msg);
    }

    public void warn(String msg, Throwable t) {
        logger.warn(msg, t);
    }

    public void warn(Exception exception) {
        logger.warn(LoggerWrapper.getExceptionInfo(exception));
    }

    public void error(String msg) {
        logger.error(msg);
    }

    public void error(String msg, Throwable t) {
        logger.error(msg, t);
    }

    public void error(Exception exception) {
        logger.error(LoggerWrapper.getExceptionInfo(exception));
    }

    public void error(Throwable throwable) {
        logger.error(LoggerWrapper.getThrowableInfo(throwable));
    }

    public boolean isDebug() {
        return logger.isDebugEnabled();
    }
}
