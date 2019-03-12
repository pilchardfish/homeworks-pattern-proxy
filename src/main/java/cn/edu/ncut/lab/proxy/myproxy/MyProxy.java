package cn.edu.ncut.lab.proxy.myproxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by IntelliJ IDEA.
 * User: pilchard
 * Date: 2019-03-12
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
public class MyProxy {
    private static final AtomicLong nextUniqueNumber = new AtomicLong();
    static Long num;

    public static Object newProxyInstance(MyClassLoader loader,
                                          Class<?>[] interfaces,
                                          MyInvocationHandler h) {
        //生成字节码
        num = nextUniqueNumber.getAndIncrement();
        try {
            String code = generateSrc(interfaces, h);
            System.out.println(code);
            //编译字节码
            String filePath = MyProxy.class.getResource("").getPath();
            System.out.println(filePath);

            File f = new File(filePath + "$MyProxy" + num + ".java");
            FileWriter fw = new FileWriter(f);
            fw.write(code);
            fw.flush();
            fw.close();
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manage = compiler.getStandardFileManager(null, null, null);
            Iterable iterable = manage.getJavaFileObjects(f);
            JavaCompiler.CompilationTask task =
                    compiler.getTask(null, manage, null, null, null, iterable);
            task.call();
            manage.close();
           //4、编译生成的.class 文件加载到JVM 中来
            Class proxyClass = loader.findClass("$MyProxy"+num);
            Constructor c = proxyClass.getConstructor(MyInvocationHandler.class);
         //   f.delete();
           //5、返回字节码重组以后的新的代理对象
            return c.newInstance(h);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private static String generateSrc(Class<?>[] interfaces, MyInvocationHandler h) {
        String ln = "\r\n";
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        StringBuilder methodTemp = new StringBuilder();
        StringBuilder paramTemp=new StringBuilder();
        StringBuilder argsTemp = new StringBuilder();
        int n = h.getClass().getName().lastIndexOf('.');
        String pkg = (n == -1) ? "" : h.getClass().getName().substring(0, n);
        sb.append("package ").append(pkg).append(";" + ln);
        for (Class<?> interfaze : interfaces)
            sb.append("import ").append(interfaze.getName()).append(";" + ln);
        sb.append("import cn.edu.ncut.lab.proxy.myproxy.MyInvocationHandler").append(";" + ln);
        sb.append("import java.lang.reflect.*").append(";" + ln);
        for (Class<?> interfaze : interfaces) {
            temp.append(interfaze.getSimpleName());
            temp.append(",");
        }
        if (temp.length() > 0)
            temp.deleteCharAt(temp.length() - 1);

        sb.append("public final class $MyProxy" + num + " implements ").append(temp).append(" {").append(ln);
        sb.append(" private MyInvocationHandler h=null;").append(ln);
        sb.append(" public $MyProxy" + num + "(MyInvocationHandler h){").append(ln);
        sb.append("     this.h=h;").append(ln);
        sb.append("}").append(ln);

        for (int i = 0; i < interfaces.length; i++) {
            for (Method method : interfaces[i].getMethods()) {
                paramTemp=new StringBuilder();
                argsTemp=new StringBuilder();
                methodTemp=new StringBuilder();
                for(Parameter p:method.getParameters()){
                    paramTemp.append(p.getType().getName()+" "+p.getName()+",");
                    argsTemp.append(p.getName()).append(",");
                }
                if(paramTemp.length()>0)
                    paramTemp.deleteCharAt(paramTemp.length()-1);
                sb.append("public ").append(method.getReturnType().getName()).append(" " + method.getName()+"("+paramTemp+")").append("{").append(ln);
                sb.append(" Object o=null;").append(ln);
                sb.append(" try{").append(ln);
                Class<?>[] params = method.getParameterTypes();
                for (Class<?> param : params)
                    methodTemp.append(param.getName() + ".class,");
                if (methodTemp.length() > 0)
                    methodTemp.deleteCharAt(methodTemp.length() - 1);
                sb.append("     Method m=").append(interfaces[i].getName()).append(".class.getMethod(\"" + method.getName() + "\",new Class[]{" + methodTemp + "});").append(ln);
                if (argsTemp.length() > 0) {
                    argsTemp.deleteCharAt(argsTemp.length() - 1);
                    argsTemp.insert(0, "new Object[]{");
                    argsTemp.append("}");
                } else {
                    argsTemp.append("(Object[])null");
                }
                sb.append("      o= h.invoke(this,m," + argsTemp.toString() + ");").append(ln);
                sb.append("     }catch(Throwable e){" + ln + "          e.printStackTrace();" + ln + "     }").append(ln);
                if (!method.getReturnType().getName().equals("void"))
                    sb.append("return ("+method.getReturnType().getName()+") o;");
                sb.append(" }" + ln);
            }

        }

        sb.append("}");
        return sb.toString();
    }
}
