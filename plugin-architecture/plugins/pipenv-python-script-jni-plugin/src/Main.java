package src;
public class Main {
    public boolean PreExecute() {
        System.out.println("PreExecute: pipenv-python-script-jni-plugin");
        String scriptDir = System.getProperty("user.dir") + "/plugins/pipenv-python-script-jni-plugin/script";
        boolean success = setup(scriptDir);
        if (!success) {
            System.out.println("Failed to setup the script directory.");
            return false;
        }
        return true;
    }
    public boolean Execute() {
        System.out.println("Execute: pipenv-python-script-jni-plugin");
        String scriptDir = System.getProperty("user.dir") + "/plugins/pipenv-python-script-jni-plugin/script";
        String[] inputParams = new String[]{"1", "2"};
        StringBuilder output = new StringBuilder();
        Boolean success = execute(scriptDir, inputParams, output);
        if (!success) {
            System.out.println("Failed to execute the script.");
            return false;
        }
        System.out.println("Output: " + output.toString());
        return true;
    }
    public boolean PostExecute() {
        System.out.println("PostExecute: pipenv-python-script-jni-plugin");
        return true;
    }

    static {
        System.loadLibrary("pipenv-python-script-jni-plugin");
    }

    private static native boolean setup(String scriptDir);
    private static native boolean execute(String scriptDir, String[] inputParams, StringBuilder output);
}