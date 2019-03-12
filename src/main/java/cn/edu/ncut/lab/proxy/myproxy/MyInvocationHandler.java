package cn.edu.ncut.lab.proxy.myproxy;

import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: pilchard
 * Date: 2019-03-12
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
public interface MyInvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable;
}
