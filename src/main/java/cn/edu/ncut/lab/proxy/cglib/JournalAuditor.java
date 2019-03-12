package cn.edu.ncut.lab.proxy.cglib;

/**
 * Created by IntelliJ IDEA.
 * User: pilchard
 * Date: 2019-03-12
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public class JournalAuditor {
    public void audit() {
        System.out.println("期刊审阅");
        System.out.println("不涉黄");
        System.out.println("不涉党政");
        System.out.println("不涉宗教");
    }
}
