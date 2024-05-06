import java.util.ArrayList;

public class Pipeline {
    private ArrayList<String> steps = new ArrayList<>();
    private PluginManager pluginManager;
    Pipeline(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }
    void addStep(String pluginName) {
        // TODO : Add validation to check if plugin exists
        steps.add(pluginName);
    }

    void Execute() {
        for (String step: steps) {
            this.pluginManager.executePlugin(step);
        }
    }
}
