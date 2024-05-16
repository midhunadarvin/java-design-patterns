# Java Plugin Architecture 

### PreRequisite 

Install pipenv : https://pypi.org/project/pipenv/#installation

### Build

```
make build
```

### Run

```
make up
```

### Write a Plugin ( Java Adapter )

This project accepts java plugins in the form of jar files. Examples are given in the plugins folder.

A plugin must follow the Design by Contract (DBC) pattern.

```
package src;
public class Main {
    public boolean PreExecute() {
        System.out.println("PreExecute: plugin 1");
        return true;
    }
    public boolean Execute() {
        System.out.println("Execute: plugin 1");
        return true;
    }
    public boolean PostExecute() {
        System.out.println("PostExecute: plugin 1");
        return true;
    }
}
```

### Write a Pipeline 

Pipeline can be used to compose different plugins to create a executable workflow.

```
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
```