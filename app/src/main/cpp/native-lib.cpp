#include <jni.h>
#include <stdlib.h>
#include <string>
#include <android/log.h>

#define ALOG  __android_log_print
#define TAG   "xxxxx: inside JNI source file native-lib.cpp "

extern "C"
JNIEXPORT jstring JNICALL
Java_com_wistron_wistronar_ASRListener_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {

    std::string hello = "1 7 Hello from C++";

    ALOG(ANDROID_LOG_INFO, TAG ,"44444");


    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_tw_com_flag_ch02_button_ASRListener_goHomeJNI(
        JNIEnv *env,
        jobject /* this */) {


    ALOG(ANDROID_LOG_INFO, TAG ,"11 will use sendenv to emulate HOME key ");


    system("sendevent /dev/input/event2 1 114 1 ; sleep 1 ; sendevent /dev/input/event2 0 0 0");

//    system("date ");
//    system("sendevent /dev/input/event2 1 114 1");
//    system("sleep 1");
//    system("sendevent /dev/input/event2 0   0 0");

    ALOG(ANDROID_LOG_INFO, TAG ,"22 will use sendenv to emulate HOME key ");


}
