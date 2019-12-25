package com.klaus.clazz;

/**
 * Created by KlausZ on 2019/8/27.
 */
public class ClassContextUtil extends SecurityManager {

    static final ClassContextUtil INSTANCE = new ClassContextUtil();

    private ClassContextUtil() {
    }

    @Override
    protected Class[] getClassContext() {
        return super.getClassContext();
    }

    public static Class<?> getCallerClass() {
        return getCallerClass(2);
    }

    public static Class<?> getCallerClass(int depth) {
        Class<?>[] context = INSTANCE.getClassContext();
        int index = depth + 2;
        int len = context.length;
        return len > index ? context[index] : context[len - 1];
    }
}
