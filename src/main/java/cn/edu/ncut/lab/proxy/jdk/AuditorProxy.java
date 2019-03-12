package cn.edu.ncut.lab.proxy.jdk;

import cn.edu.ncut.lab.proxy.Auditor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by IntelliJ IDEA.
 * User: pilchard
 * Date: 2019-03-12
 * Time: 13:53
 * To change this template use File | Settings | File Templates.
 */
public class AuditorProxy implements InvocationHandler {
    private Auditor auditor;
    public  Object getInstance(Auditor auditor){
        this.auditor=auditor;
        Class<?>clazz=auditor.getClass();
       return   Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object o=method.invoke(auditor,args);
        after();
        return o;
    }
   private void  before(){
       System.out.println("检查报送对象");
    }
    private void after() {
        System.out.println("生成检查报告");
    }
}
