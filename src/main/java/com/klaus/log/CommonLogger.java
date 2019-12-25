package com.klaus.log;

import com.klaus.clazz.ClassContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用的Logger调用方式
 * Created by KlausZ on 2019/12/25.
 */
public class CommonLogger {


    private static Logger logger = getLogger(CommonLogger.class);

    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz.getName());
    }

    private static Logger getLoggerByCaller() {
        try {
            return getLogger(ClassContextUtil.getCallerClass(2));
        } catch (Throwable e) {
            return logger;
        }
    }

    public static boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    public static void trace(String msg) {
        try {
            getLoggerByCaller().trace(msg);
        } catch (Throwable ignored) {
        }
    }

    public static void trace(String format, Object arg) {
        getLoggerByCaller().trace(format, arg);
    }

    public static void trace(String format, Object arg1, Object arg2) {
        getLoggerByCaller().trace(format, arg1, arg2);
    }

    public static void trace(String format, Object... arguments) {
        getLoggerByCaller().trace(format, arguments);
    }

    public static void trace(String msg, Throwable t) {
        getLoggerByCaller().trace(msg, t);
    }

    public static boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public static void debug(String msg) {
        getLoggerByCaller().debug(msg);
    }

    public static void debug(String format, Object arg) {
        getLoggerByCaller().debug(format, arg);
    }

    public static void debug(String format, Object arg1, Object arg2) {
        getLoggerByCaller().debug(format, arg1, arg2);
    }

    public static void debug(String format, Object... arguments) {
        getLoggerByCaller().debug(format, arguments);
    }

    public static void debug(String msg, Throwable t) {
        getLoggerByCaller().debug(msg, t);
    }

    public static boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    public static void info(String msg) {
        getLoggerByCaller().info(msg);
    }

    public static void info(String format, Object arg) {
        getLoggerByCaller().info(format, arg);
    }

    public static void info(String format, Object arg1, Object arg2) {
        getLoggerByCaller().info(format, arg1, arg2);
    }

    public static void info(String format, Object... arguments) {
        getLoggerByCaller().info(format, arguments);
    }

    public static void info(String msg, Throwable t) {
        getLoggerByCaller().info(msg, t);
    }

    public static boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    public static void warn(String msg) {
        getLoggerByCaller().warn(msg);
    }

    public static void warn(String format, Object arg) {
        getLoggerByCaller().warn(format, arg);
    }

    public static void warn(String format, Object... arguments) {
        getLoggerByCaller().warn(format, arguments);
    }

    public static void warn(String format, Object arg1, Object arg2) {
        getLoggerByCaller().warn(format, arg1, arg2);
    }

    public static void warn(String msg, Throwable t) {
        getLoggerByCaller().warn(msg, t);
    }

    public static boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    public static void error(String msg) {
        getLoggerByCaller().error(msg);
    }

    public static void error(String format, Object arg) {
        getLoggerByCaller().error(format, arg);
    }

    public static void error(String format, Object arg1, Object arg2) {
        getLoggerByCaller().error(format, arg1, arg2);
    }

    public static void error(String format, Object... arguments) {
        getLoggerByCaller().error(format, arguments);
    }

    public static void error(String msg, Throwable t) {
        getLoggerByCaller().error(msg, t);
    }
}
