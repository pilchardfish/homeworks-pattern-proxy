package cn.edu.ncut.lab.proxy.cglib;


import cn.edu.ncut.lab.proxy.Auditor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: pilchard
 * Date: 2019-03-12
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
public class AuditorCglibProxy implements MethodInterceptor {
    private JournalAuditor journalAuditor;
    public  Object getInstance(JournalAuditor journalAuditor){
        this.journalAuditor=journalAuditor;
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(journalAuditor.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object object=method.invoke(journalAuditor);
        after();
        return object;
    }
    private void  before(){
        System.out.println("检查报送对象");
    }
    private void after() {
        System.out.println("生成检查报告");
    }
}
