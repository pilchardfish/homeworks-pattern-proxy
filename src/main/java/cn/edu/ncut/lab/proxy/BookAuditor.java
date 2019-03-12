package cn.edu.ncut.lab.proxy;

/**
 * Created by IntelliJ IDEA.
 * User: pilchard
 * Date: 2019-03-12
 * Time: 13:51
 * To change this template use File | Settings | File Templates.
 */
public class BookAuditor implements Auditor {
    public void audit() {
        System.out.println("图书审阅");
        System.out.println("不涉黄");
        System.out.println("不涉党政");
        System.out.println("不涉宗教");
    }

    @Override
    public String say(String name) {
        System.out.println("执行对话"+name);
        return "Hello"+name;
    }

    @Override
    public void print(String name) {
        System.out.println(name);
    }
}
