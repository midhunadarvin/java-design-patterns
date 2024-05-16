package src;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {
    public boolean PreExecute() {
        System.out.println("PreExecute: pipenv-python-script-plugin");
        // should get from computation context
        String scriptDir = System.getProperty("user.dir") + "/plugins/pipenv-python-script-plugin/script";
        return setup(scriptDir);
    }
    private boolean setup(String scriptDir) {
        System.out.println("Setup: " + scriptDir);
        String command = "cd " + scriptDir + " && pipenv install";
        System.out.println("Command: " + command);

        // use ProcessBuilder to execute the command
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.out.println("Failed to setup the script directory. Exit code: " + exitCode + ", Output: " + readToStringFromInputStreamReader(process.getErrorStream()));
                return false;
            }
        } catch (Exception e) {
            System.out.println("Failed to setup the script directory. Exception: " + e.getMessage());
            return false;
        }
        return true;
    }
    public boolean Execute() {
        System.out.println("Execute: pipenv-python-script-plugin");
        String scriptDir = System.getProperty("user.dir") + "/plugins/pipenv-python-script-plugin/script";
        boolean success = setup(scriptDir);
        if (!success) {
            System.out.println("Failed to setup the script directory.");
            return false;
        }
        String[] inputParams = new String[]{"1", "2"};
        // should be getting scriptDir, inputParams from computation context
        String out = execute(scriptDir, inputParams);
        if (out == null) {
            System.out.println("Failed to execute the script.");
            return false;
        }

        System.out.println("Output: " + out);
        return true;
    }
    private String execute(String scriptDir, String[] inputParams) {
        System.out.println("Execute: " + scriptDir);
        String command = "cd " + scriptDir + " && pipenv run python main.py " + String.join(" ", inputParams);
        System.out.println("Command: " + command);

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                // get error output
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                StringBuilder errorOutput = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    errorOutput.append(line + "\n");
                }
                System.out.println("Failed to execute the script. Exit code: " + exitCode + ", Output: " + errorOutput.toString());
                return null;
            }
            return readToStringFromInputStreamReader(process.getInputStream());
        } catch (Exception e) {
            System.out.println("Failed to execute the script. Exception: " + e.getMessage());
            return null;
        }
    }
    public boolean PostExecute() {
        System.out.println("PostExecute: pipenv-python-script-plugin");
        return true;
    }

    private String readToStringFromInputStreamReader(InputStream stream) {
        InputStreamReader reader = new InputStreamReader(stream);
        StringBuilder output = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            System.out.println("Failed to read from input stream reader. Exception: " + e.getMessage());
        }
        return output.toString();
    }
}