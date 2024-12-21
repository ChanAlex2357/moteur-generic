package mg.jca.gfja.loader;

public class PathUtils {
    public static String getPathOnContext(String path) {
        return System.getProperty("user.dir") + "/"+path;
    }
}
