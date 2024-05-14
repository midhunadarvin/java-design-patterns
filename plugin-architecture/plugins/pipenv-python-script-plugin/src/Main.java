package src;
public class Main {
    public boolean PreExecute() {
        System.out.println("PreExecute: pipenv-python-script-plugin");
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
        StringBuilder output = new StringBuilder();
        success = execute(scriptDir, inputParams, output);
        if (!success) {
            System.out.println("Failed to execute the script.");
            return false;
        }
        System.out.println("Output: " + output.toString());
        return true;
    }
    public boolean PostExecute() {
        System.out.println("PostExecute: pipenv-python-script-plugin");
        return true;
    }

    static {
        System.load(System.getProperty("user.dir") + "/plugins/pipenv-python-script-plugin/lib/src_Main.dylib");
    }

    private static native boolean setup(String scriptDir);
    private static native boolean execute(String scriptDir, String[] inputParams, StringBuilder output);
}