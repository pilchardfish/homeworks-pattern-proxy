import cn.edu.ncut.lab.proxy.cglib.AuditorCglibProxy;
import cn.edu.ncut.lab.proxy.cglib.JournalAuditor;

/**
 * Created by IntelliJ IDEA.
 * User: pilchard
 * Date: 2019-03-12
 * Time: 14:14
 * To change this template use File | Settings | File Templates.
 */
public class AuditorCglibProxyTest {
    public static void main(String[] args) {
        JournalAuditor auditor=(JournalAuditor)new AuditorCglibProxy().getInstance(new JournalAuditor());
        auditor.audit();
    }
}
