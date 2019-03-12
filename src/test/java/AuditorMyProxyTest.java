import cn.edu.ncut.lab.proxy.Auditor;
import cn.edu.ncut.lab.proxy.BookAuditor;
import cn.edu.ncut.lab.proxy.myproxy.AuditorMyProxy;

import java.lang.reflect.Array;

/**
 * Created by IntelliJ IDEA.
 * User: pilchard
 * Date: 2019-03-12
 * Time: 20:31
 * To change this template use File | Settings | File Templates.
 */
public class AuditorMyProxyTest {
    public static void main(String[] args) {
        Auditor bookAuditor = (Auditor) new AuditorMyProxy().getInstance(new BookAuditor());
        bookAuditor.audit();
        bookAuditor.print("Hello World!");
        System.out.println(bookAuditor.say("Pilchard"));
    }
}
