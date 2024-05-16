package com.mycompany.app;
import org.graalvm.polyglot.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
//        Context context = Context.newBuilder().allowIO(true).build();
        String VENV_EXECUTABLE = App.class.getClassLoader().getResource(".venv/bin/python3.12").getPath();
        Context context = Context.newBuilder("python").
                allowAllAccess(true).
                option("python.PythonPath", System.getProperty("user.dir") + "/src/main/resources").
                option("python.ForceImportSite", "true").
                option("python.Executable", VENV_EXECUTABLE).
                build();

        Value array = context.eval("python", "[1,2,42,4]");
        int result = array.getArrayElement(2).asInt();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("test.py");
//        File file = new File("/test.py");
//        Source source = Source.newBuilder("python", file).build();
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (inputStream, StandardCharsets.UTF_8))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }
        Source source = Source.newBuilder("python", new File(System.getProperty("user.dir") + "/src/main/resources/test.py")).build();
        context.eval(source);
        System.out.println(result);
    }
}
