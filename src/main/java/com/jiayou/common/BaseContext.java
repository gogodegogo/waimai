package com.jiayou.common;

public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static long getid() {
        return threadLocal.get();
    }

    public static void setid(long id) {
        threadLocal.set(id);
    }
}
