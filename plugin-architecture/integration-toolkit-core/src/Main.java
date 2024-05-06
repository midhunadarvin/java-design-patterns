public class Main {
    public static void main(String[] args) {
        // Load plugins
        PluginManager pluginManager = new PluginManager();
        String pluginsFolderPath = System.getenv("PLUGINS_FOLDER");
        pluginManager.loadPlugin("plugin-1", pluginsFolderPath + "/plugin-1/plugin-1.jar");
        pluginManager.loadPlugin("plugin-2", pluginsFolderPath + "/plugin-2/plugin-2.jar");

        // Create Pipeline ( Declare the different steps of the pipeline )
        Pipeline pipeline = new Pipeline(pluginManager);
        pipeline.addStep("plugin-1");
        pipeline.addStep("plugin-2");

        // Execute Pipeline
        pipeline.Execute();
    }
}