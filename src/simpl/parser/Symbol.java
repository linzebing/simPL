package simpl.parser;

import java.util.HashMap;

public class Symbol {

    private String name;

    private Symbol(String n) {
        name = n;
    }

    public String toString() {
        return name;
    }

    private static HashMap<String, Symbol> dict = new HashMap<String, Symbol>();

    /**
     * Make return the unique symbol associated with a string. Repeated calls to <tt>symbol("abc")</tt> will return the
     * same Symbol.
     */
    public static Symbol symbol(String n) {
        String u = n.intern(); //检查字符串池中是否存在这么一个字符串 存在则返回 ； 否则就添加到字符串池中 然后返回其引用
        Symbol s = dict.get(u);
        if (s == null) {
            s = new Symbol(u);
            dict.put(u, s);
        }
        return s;
    }
}
