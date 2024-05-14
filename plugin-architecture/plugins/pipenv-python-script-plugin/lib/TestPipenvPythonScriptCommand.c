#include "PipenvPythonScriptCommand.h"
#include <stdio.h>

int main() {
    const char* scriptDir = "/Users/chistadata/sandbox/java-design-patterns/plugin-architecture/plugins/pipenv-python-script-plugin/script";
    
    // Test the setup function
    int setupResult = setup(scriptDir);
    if (setupResult) {
        printf("Setup completed successfully.\n");
    } else {
        printf("Setup failed.\n");
    }

    // Prepare input parameters
    const char* params[] = {"1", "2", NULL};

    // Test the execute function
    char* output = NULL;
    int executeResult = execute(scriptDir, params, &output);
    if (executeResult) {
        printf("Execution completed successfully. Output:\n%s\n", output);
    } else {
        printf("Execution failed.\n");
    }

    // Free the allocated output memory
    free(output);

    return 0;
}
