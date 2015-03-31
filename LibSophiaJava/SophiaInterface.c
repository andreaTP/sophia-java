#include<jni.h>
#include<stdio.h>
#include<stdlib.h>
#include"eu_unicredit_sophia_SophiaInterface.h"

#include"sophia.h"

/*
Memory management model
*/
jobject JNICALL Java_eu_unicredit_sophia_SophiaInterface_allocate_1mem
  (JNIEnv * env, jobject obj, jlong capacity)
{
    void* address = (void *)malloc((long)capacity);
    return Java_eu_unicredit_sophia_SophiaInterface_get_1mem(env, obj, (jlong)address, capacity);
}

jobject Java_eu_unicredit_sophia_SophiaInterface_get_1mem
  (JNIEnv * env , jobject obj, jlong address, jlong capacity)
{
    jobject buffer = (*env)->NewDirectByteBuffer(env, (void*)address, capacity);
    jclass cls = (*env)->FindClass(env, "eu/unicredit/sophia/MemoryArea");
    if (cls == 0) {
        printf("Find Class Failed.\n");
        return NULL;
    }

    jmethodID constructor = (*env)->GetMethodID(env, cls, "<init>", "(JLjava/nio/ByteBuffer;J)V");
    if (constructor == 0) {
        printf("Find method Failed.\n");
        return NULL;
    }

    jobject result = (*env)->NewObject(env, cls, constructor, (jlong)address, buffer, capacity);

    return result;
}

jlong Java_eu_unicredit_sophia_SophiaInterface_get_1mem_1capacity
  (JNIEnv * env, jobject obj, jobject buffer)
{
    return (*env)->GetDirectBufferCapacity(env, buffer);
}

jlong Java_eu_unicredit_sophia_SophiaInterface_get_1mem_1address
  (JNIEnv * env, jobject obj, jobject buffer)
{
    return (jlong)(*env)->GetDirectBufferAddress(env, buffer);
}

void JNICALL Java_eu_unicredit_sophia_SophiaInterface_free_1mem
  (JNIEnv * env, jobject obj, jlong address) {
    free((void*)address);
    return;
}


/*
Sophia integration
v1.2
*/
jlong JNICALL Java_eu_unicredit_sophia_SophiaInterface_sp_1env
  (JNIEnv * env, jobject obj)
{
    return (jlong)sp_env();
}

jlong JNICALL Java_eu_unicredit_sophia_SophiaInterface_sp_1ctl
  (JNIEnv * env, jobject obj, jlongArray args)
{
    //devo verificare la trasformazione da array a vararg se si può fare meglio...
    jlong* array = (*env)->GetLongArrayElements(env, args,JNI_FALSE);
    jint size = (*env)->GetArrayLength(env, args);
    void* ret = NULL;
    switch(size) {
      case 0:
        printf("Not supported\n");
        break;
      case 1:
        ret = sp_ctl((void*)array[0]);
        break;
      default:
        printf("Not supported\n");
        break;
    };
    return (jlong)ret;
}

jlong JNICALL Java_eu_unicredit_sophia_SophiaInterface_sp_1object
  (JNIEnv * env, jobject obj, jlongArray args)
{
    jlong* array = (*env)->GetLongArrayElements(env, args,JNI_FALSE);
    jint size = (*env)->GetArrayLength(env, args);
    void* ret = NULL;
    switch(size) {
      case 0:
        printf("Not supported\n");
        break;
      case 1:
        ret = sp_object((void*)array[0]);
        break;
      default:
        printf("Not supported\n");
        break;
    };
    return (jlong)ret;
}

jint JNICALL Java_eu_unicredit_sophia_SophiaInterface_sp_1open
  (JNIEnv * env, jobject obj, jlongArray args)
{
    jlong* array = (*env)->GetLongArrayElements(env, args,JNI_FALSE);
    jint size = (*env)->GetArrayLength(env, args);
    int ret = -1;
    switch(size) {
      case 0:
        printf("Not supported\n");
        break;
      case 1:
        ret = sp_open((void*)array[0]);
        break;
      default:
        printf("Not supported\n");
        break;
    };
    return (jint)ret;
}

jint JNICALL Java_eu_unicredit_sophia_SophiaInterface_sp_1destroy
  (JNIEnv * env, jobject obj, jlongArray args)
{
    jlong* array = (*env)->GetLongArrayElements(env, args,JNI_FALSE);
    jint size = (*env)->GetArrayLength(env, args);
    int ret = -1;
    switch(size) {
      case 0:
        printf("Not supported\n");
        break;
      case 1:
        ret = sp_destroy((void*)array[0]);
        break;
      default:
        printf("Not supported\n");
        break;
    };
    return (jint)ret;
}

jint JNICALL Java_eu_unicredit_sophia_SophiaInterface_sp_1error
  (JNIEnv * env, jobject obj, jlongArray args)
{
    jlong* array = (*env)->GetLongArrayElements(env, args,JNI_FALSE);
    jint size = (*env)->GetArrayLength(env, args);
    int ret = -1;
    switch(size) {
      case 0:
        printf("Not supported\n");
        break;
      case 1:
        ret = sp_error((void*)array[0]);
        break;
      default:
        printf("Not supported\n");
        break;
    };
    return (jint)ret;
}

jint JNICALL Java_eu_unicredit_sophia_SophiaInterface_sp_1set
  (JNIEnv * env, jobject obj, jlongArray args)
{
    jlong* array = (*env)->GetLongArrayElements(env, args,JNI_FALSE);
    jint size = (*env)->GetArrayLength(env, args);
    int ret = -1;
    switch(size) {
      case 0:
        printf("Not supported\n");
        break;
      case 1:
        printf("Not supported\n");
        break;
      case 2:
        ret = sp_set((void*)array[0],
                     (void*)array[1]);
        break;
      case 3:
        ret = sp_set((void*)array[0],
                     (void*)array[1],
                     (void*)array[2]);
        break;
      case 4:
        ret = sp_set((void*)array[0],
                     (void*)array[1],
                     (void*)array[2],
                     (void*)array[3]);
        break;
      default:
        printf("Not supported\n");
        break;
    };
    return (jint)ret;
}

jint JNICALL Java_eu_unicredit_sophia_SophiaInterface_sp_1delete
  (JNIEnv * env, jobject obj, jlongArray args)
{
    jlong* array = (*env)->GetLongArrayElements(env, args,JNI_FALSE);
    jint size = (*env)->GetArrayLength(env, args);
    int ret = -1;
    switch(size) {
      case 0:
        printf("Not supported\n");
        break;
      case 1:
        printf("Not supported\n");
        break;
      case 2:
        ret = sp_delete((void*)array[0],
                        (void*)array[1]);
        break;
      default:
        printf("Not supported\n");
        break;
    };
    return (jint)ret;
}

jlong JNICALL Java_eu_unicredit_sophia_SophiaInterface_sp_1get
  (JNIEnv * env, jobject obj, jlongArray args)
{
    jlong* array = (*env)->GetLongArrayElements(env, args,JNI_FALSE);
    jint size = (*env)->GetArrayLength(env, args);
    void* ret = NULL;
    switch(size) {
      case 0:
        printf("Not supported\n");
        break;
      case 1:
        printf("Not supported\n");
        break;
      case 2:
        ret = sp_get((void*)array[0],
                     (void*)array[1]);
        break;
      case 3:
        ret = sp_get((void*)array[0],
                     (void*)array[1],
                     (void*)array[2]);
        break;
      default:
        printf("Not supported\n");
        break;
    };
    return (jlong)ret;
}

jint JNICALL Java_eu_unicredit_sophia_SophiaInterface_sp_1drop
  (JNIEnv * env, jobject obj, jlongArray args)
{
    jlong* array = (*env)->GetLongArrayElements(env, args,JNI_FALSE);
    jint size = (*env)->GetArrayLength(env, args);
    int ret = -1;
    switch(size) {
      case 0:
        printf("Not supported\n");
        break;
      case 1:
        ret = sp_drop((void*)array[0]);
        break;
      default:
        printf("Not supported\n");
        break;
    };
    return (jint)ret;
}

jint JNICALL Java_eu_unicredit_sophia_SophiaInterface_sp_1commit
  (JNIEnv * env, jobject obj, jlongArray args)
{
    jlong* array = (*env)->GetLongArrayElements(env, args,JNI_FALSE);
    jint size = (*env)->GetArrayLength(env, args);
    int ret = -1;
    switch(size) {
      case 0:
        printf("Not supported\n");
        break;
      case 1:
	ret = sp_commit((void*)array[0]);
        break;
      default:
        printf("Not supported\n");
        break;
    };
    return (jint)ret;
}

jlong JNICALL Java_eu_unicredit_sophia_SophiaInterface_sp_1begin
  (JNIEnv * env, jobject obj, jlongArray args)
{
    jlong* array = (*env)->GetLongArrayElements(env, args,JNI_FALSE);
    jint size = (*env)->GetArrayLength(env, args);
    void* ret = NULL;
    switch(size) {
      case 0:
        printf("Not supported\n");
        break;
      case 1:
        ret = sp_begin((void*)array[0]);
        break;
      default:
        printf("Not supported\n");
        break;
    };
    return (jlong)ret;
}

jint JNICALL Java_eu_unicredit_sophia_SophiaInterface_sp_1prepare
  (JNIEnv * env, jobject obj, jlongArray args)
{
    //jlong* array = (*env)->GetLongArrayElements(env, args,JNI_FALSE);
    jint size = (*env)->GetArrayLength(env, args);
    int ret = -1;
    switch(size) {
      default:
        printf("Not supported\n");
        break;
    };
    return (jint)ret;
}

jlong JNICALL Java_eu_unicredit_sophia_SophiaInterface_sp_1type
  (JNIEnv * env, jobject obj, jlongArray args)
{
    //jlong* array = (*env)->GetLongArrayElements(env, args,JNI_FALSE);
    jint size = (*env)->GetArrayLength(env, args);
    void* ret = NULL;
    switch(size) {
      default:
        printf("Not supported\n");
        break;
    };
    return (jlong)ret;
}

jlong JNICALL Java_eu_unicredit_sophia_SophiaInterface_sp_1cursor
  (JNIEnv * env, jobject obj, jlongArray args)
{
    jlong* array = (*env)->GetLongArrayElements(env, args,JNI_FALSE);
    jint size = (*env)->GetArrayLength(env, args);
    void* ret = NULL;
    switch(size) {
      case 0:
        printf("Not supported\n");
        break;
      case 1:
        ret = sp_cursor((void*)array[0]);
        break;
      case 2:
        ret = sp_cursor((void*)array[0],
                        (void*)array[1]);
        break;
      default:
        printf("Not supported\n");
        break;
    };
    return (jlong)ret;
}
