#include <jni.h>
#include "PipenvPythonScriptCommand.h"
#include <string.h>
#include <stdlib.h>

// Helper function to convert jstring to char*
static char* jstringToChar(JNIEnv *env, jstring jStr) {
    if (!jStr) return NULL;

    const char *temp = (*env)->GetStringUTFChars(env, jStr, NULL);
    if (!temp) return NULL;

    char *result = strdup(temp);  // Allocate memory for the C string
    (*env)->ReleaseStringUTFChars(env, jStr, temp);  // Release Java string memory

    return result;
}

// Native method to set up the environment
JNIEXPORT jboolean JNICALL Java_src_Main_setup(JNIEnv *env, jclass clazz, jstring jScriptDir) {
    char *scriptDir = jstringToChar(env, jScriptDir);
    if (!scriptDir) return JNI_FALSE;

    int result = setup(scriptDir);  // Call the C function
    free(scriptDir);  // Free the dynamically allocated memory for scriptDir

    return result ? JNI_TRUE : JNI_FALSE;
}

// Native method to execute the script
JNIEXPORT jboolean JNICALL Java_src_Main_execute(JNIEnv *env, jclass clazz, jstring jScriptDir, jobjectArray jInputParams, jobject jOutput) {
    char *scriptDir = jstringToChar(env, jScriptDir);
    if (!scriptDir) return JNI_FALSE;

    int numParams = (*env)->GetArrayLength(env, jInputParams);
    char **inputParams = (char **)malloc(sizeof(char*) * (numParams + 1));
    if (!inputParams) {
        free(scriptDir);
        return JNI_FALSE;
    }

    for (int i = 0; i < numParams; i++) {
        jstring jParam = (jstring) (*env)->GetObjectArrayElement(env, jInputParams, i);
        inputParams[i] = jstringToChar(env, jParam);
    }
    inputParams[numParams] = NULL;  // Ensure the last element is NULL

    char *output = NULL;
    int success = execute(scriptDir, (const char **)inputParams, &output);

    if (success) {
        // Assuming jOutput is a StringBuilder
        jclass stringBuilderClass = (*env)->GetObjectClass(env, jOutput);
        jmethodID appendMethod = (*env)->GetMethodID(env, stringBuilderClass, "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;");
        if (appendMethod) {
            jstring jOutputStr = (*env)->NewStringUTF(env, output);
            (*env)->CallObjectMethod(env, jOutput, appendMethod, jOutputStr);
            (*env)->DeleteLocalRef(env, jOutputStr);
        } else {
            success = 0;  // Failed to get the append method
        }
    }

    // Clean up
    free(scriptDir);
    for (int i = 0; i < numParams; i++) {
        free(inputParams[i]);
    }
    free(inputParams);
    free(output);

    return success ? JNI_TRUE : JNI_FALSE;
}
