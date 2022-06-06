package top.yudoge.vpadserverj.utils;

public class ArrayUtils {
    public static <T> boolean in(T t, T[] ts) {
        for (T _t: ts) {
            if (t.equals(_t)) return true;
        }
        return false;
    }
}
