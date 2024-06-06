package com.chistadata.plugins;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class Main
{
    public boolean PreExecute() {
        System.out.println("PreExecute: plugin maven");
        return true;
    }

    public boolean Execute() throws IOException, TemplateException {
        String templateRoot = "/Users/vishal/source/java-design-patterns/plugin-architecture/plugins/plugin-maven/src/templates";

        Configuration cfg = TemplateEngine.init(templateRoot);

        Map<String, Object> dm = new HashMap<>();
        dm.put("user","John Doe");

        Template temp = cfg.getTemplate("test.ftlh");

        Writer out = new OutputStreamWriter(System.out);
        temp.process(dm, out);
        return true;
    }

    public boolean PostExecute() {
        System.out.println("PostExecute: plugin maven");
        return true;
    }
}
