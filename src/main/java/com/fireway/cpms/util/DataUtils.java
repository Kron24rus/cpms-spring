package com.fireway.cpms.util;


import com.fireway.cpms.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {
    private static DateFormat STANDARD_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static DateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    public static String formatDate(Date date) {
        return STANDARD_DATE_FORMAT.format(date);
    }

    public static Timestamp parseDate(String date) throws BadRequestException {
        try {
            return (Timestamp) STANDARD_DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    public static Integer parseInt(String string) throws BadRequestException {
        try {
            return Integer.parseInt(StringUtils.trim(string));
        } catch (NumberFormatException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public static Boolean parseBoolean(String string) throws BadRequestException {
        if (StringUtils.equalsIgnoreCase("true", StringUtils.trim(string))) {
            return true;
        } else if (StringUtils.equalsIgnoreCase("false", StringUtils.trim(string))) {
            return false;
        } else {
            throw new BadRequestException("Unknown boolean value");
        }
    }

    public static boolean isAnyNull(Object... params) {
        for (Object param: params) {
            if (param == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPostMethod(String method) {
        String POST_METHOD = "post";
        return StringUtils.equalsIgnoreCase(method, POST_METHOD);
    }

    public static boolean isPutMethod(String method) {
        String PUT_METHOD = "put";
        return StringUtils.equalsIgnoreCase(method, PUT_METHOD);
    }

    public static boolean isDeleteMethod(String method) {
        String DELETE_METHOD = "delete";
        return StringUtils.equalsIgnoreCase(method, DELETE_METHOD);
    }
}
