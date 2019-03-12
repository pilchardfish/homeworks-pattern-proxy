package cn.edu.ncut.lab.proxy.myproxy;

import cn.edu.ncut.lab.proxy.Auditor;

import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: pilchard
 * Date: 2019-03-12
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
public class AuditorMyProxy implements MyInvocationHandler{
    private Auditor auditor;
    public  Object getInstance(Auditor auditor){
        this.auditor=auditor;
        Class<?>clazz=auditor.getClass();
        return   MyProxy.newProxyInstance(new MyClassLoader(),clazz.getInterfaces(),this);
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
