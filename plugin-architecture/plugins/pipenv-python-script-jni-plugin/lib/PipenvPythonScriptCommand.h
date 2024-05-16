#ifndef PIPENV_PYTHON_SCRIPT_COMMAND_H
#define PIPENV_PYTHON_SCRIPT_COMMAND_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifdef __cplusplus
extern "C" {
#endif

static inline int setup(const char* scriptDir) {
    char command[1024];
    int snprintfResult = snprintf(command, sizeof(command), "cd %s && pipenv install", scriptDir);
    if (snprintfResult < 0 || snprintfResult >= sizeof(command)) {
        fprintf(stderr, "Error formatting setup command.\n");
        return 0;
    }

    fprintf(stderr, "Executing setup command: %s\n", command);
    
    int result = system(command);
    if (result != 0) {
        fprintf(stderr, "Error during setup with exit code: %d\n", result);
    }
    return result == 0;
}

static inline int execute(const char* scriptDir, const char** inputParams, char** output) {
    char command[4096];
    int commandLength = snprintf(command, sizeof(command), "cd %s && pipenv run python main.py", scriptDir);
    if (commandLength < 0 || commandLength >= sizeof(command)) {
        fprintf(stderr, "Error formatting execute command.\n");
        return 0;
    }

    for (int i = 0; inputParams[i] != NULL; i++) {
        int paramLength = snprintf(command + commandLength, sizeof(command) - commandLength, " %s", inputParams[i]);
        if (paramLength < 0 || paramLength >= (sizeof(command) - commandLength)) {
            fprintf(stderr, "Error adding input parameter to command.\n");
            return 0;
        }
        commandLength += paramLength;
    }

    fprintf(stderr, "Executing Python script: %s\n", command);
    
    FILE* pipe = popen(command, "r");
    if (!pipe) {
        fprintf(stderr, "Failed to execute Python script command.\n");
        return 0;
    }

    char buffer[128];
    char *temp = NULL;
    size_t totalRead = 0;
    *output = NULL;

    while (fgets(buffer, sizeof(buffer), pipe) != NULL) {
        size_t bufferLen = strlen(buffer);
        temp = realloc(*output, totalRead + bufferLen + 1);  // +1 for null terminator
        if (!temp) {
            free(*output);
            fprintf(stderr, "Failed to allocate memory.\n");
            pclose(pipe);
            return 0;
        }
        *output = temp;
        strcpy(*output + totalRead, buffer);
        totalRead += bufferLen;
    }

    int closeResult = pclose(pipe);
    if (closeResult != 0) {
        fprintf(stderr, "Error during execution with exit code: %d\n", closeResult);
        free(*output);
        return 0;
    }

    return 1;  // Success
}

#ifdef __cplusplus
}
#endif

#endif // PIPENV_PYTHON_SCRIPT_COMMAND_H
