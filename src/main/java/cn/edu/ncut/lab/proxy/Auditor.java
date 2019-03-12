package cn.edu.ncut.lab.proxy;

/**
 * Created by IntelliJ IDEA.
 * User: pilchard
 * Date: 2019-03-12
 * Time: 13:50
 * To change this template use File | Settings | File Templates.
 */
public interface Auditor {
    void audit();
    void print(String name);
    String say(String name);
}
