import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class PluginManager {
    Map<String, Class<?>> plugins = new HashMap<>();
    void loadPlugin(String pluginName, String pluginClassPath, String filePath) {
        try {
            File file = new File(filePath);
            URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});

            plugins.put(pluginName, classLoader.loadClass(pluginClassPath));
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
    }

    boolean executePlugin(String pluginName) {
        try {
            Class<?> plugin = plugins.get(pluginName);
            Method preExecute = plugin.getDeclaredMethod("PreExecute");
            Method execute = plugin.getDeclaredMethod("Execute");
            Method postExecute = plugin.getDeclaredMethod("PostExecute");

            Object obj = plugin.getDeclaredConstructor().newInstance();
            if ((boolean) preExecute.invoke(obj)) {
                boolean result = (boolean) execute.invoke(obj);
                postExecute.invoke(obj);
                return result;
            }
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
        return false;
    }
}
