import cn.edu.ncut.lab.proxy.Auditor;
import cn.edu.ncut.lab.proxy.BookAuditor;
import cn.edu.ncut.lab.proxy.jdk.AuditorProxy;
import sun.misc.ProxyGenerator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: pilchard
 * Date: 2019-03-12
 * Time: 14:04
 * To change this template use File | Settings | File Templates.
 */
public class AuditorProxyTest {
    public static void main(String[] args) {
        Auditor bookAuditor=(Auditor)new AuditorProxy().getInstance(new BookAuditor());
       byte[] b= ProxyGenerator.generateProxyClass("Proxy0",new Class[]{Auditor.class});
       try {
           FileOutputStream o=new FileOutputStream(new File("d:/proxy0.class"));
           o.write(b);
           o.write(b);
           o.flush();
           o.close();
       }catch (Exception e)
       {
           e.printStackTrace();
       }
        bookAuditor.audit();

    }
}
