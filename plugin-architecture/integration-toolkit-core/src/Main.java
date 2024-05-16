public class Main {
    public static void main(String[] args) {
        // Load plugins
        PluginManager pluginManager = new PluginManager();
        String pluginsFolderPath = System.getenv("PLUGINS_FOLDER");
        pluginManager.loadPlugin("plugin-1", "src.Main", pluginsFolderPath + "/plugin-1/plugin-1.jar");
        pluginManager.loadPlugin("plugin-2", "src.Main", pluginsFolderPath + "/plugin-2/plugin-2.jar");
        pluginManager.loadPlugin("pipenv-python-script-plugin", "src.Main",pluginsFolderPath + "/pipenv-python-script-plugin/pipenv-python-script-plugin.jar");
        pluginManager.loadPlugin("pipenv-python-script-jni-plugin", "src.Main",pluginsFolderPath + "/pipenv-python-script-jni-plugin/pipenv-python-script-jni-plugin.jar");
        pluginManager.loadPlugin("plugin-maven", "com.chistadata.plugins.Main",pluginsFolderPath + "/plugin-maven/target/plugin-maven-1.0-SNAPSHOT.jar");

        // Create Pipeline ( Declare the different steps of the pipeline )
        Pipeline pipeline = new Pipeline(pluginManager);
        pipeline.addStep("plugin-1");
        pipeline.addStep("plugin-2");
        pipeline.addStep("pipenv-python-script-plugin");
        pipeline.addStep("pipenv-python-script-jni-plugin");
        pipeline.addStep("plugin-maven");

        // Execute Pipeline
        pipeline.Execute();
    }
}