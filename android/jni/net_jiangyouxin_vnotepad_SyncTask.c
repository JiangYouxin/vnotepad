#include <jni.h>

#include "xdiff.h"

void fill_mmfile(JNIEnv env, mmfile_t *native_mf, jbyteArray mf) {
    native_mf->ptr = (char *)env->GetByteArrayElements(mf, 0);
    native_mf->size = env->GetArrayLength(mf, 0);
}

JNIEXPORT jbyteArray JNICALL Java_net_jiangyouxin_vnotepad_SyncTask_xdl_1merge
    (JNIEnv *env, 
     jobject obj, 
     jbyteArray orig, 
     jbyteArray mf1, 
     jbyteArray mf2, 
     jint flags, 
     jint marker_size, 
     jint level, 
     jint favor, 
     jint style, 
     jstring ancestor, 
     jstring file1, 
     jstring file2) 
{
    mmfile_t native_orig;
    mmfile_t native_mf1;
    mmfile_t native_mf2;
    mmbuffer_t native_result = { 0, 0 };
    xmparam_t xmp;
    char *native_ancestor;
    char *native_file1;
    char *native_file2;
    
    fill_mmfile(*env, &native_orig, orig);
    fill_mmfile(*env, &native_mf1, mf1);
    fill_mmfile(*env, &native_mf2, mf2);

    xmp.xpp.flags = flags;
    xmp.marker_size = marker_size;
    xmp.level = level;
    xmp.favor = favor;
    xmp.style = style;
    xmp.ancestor = (*env)->GetStringUTFChars(env, ancestor, 0);
    xmp.file1 = (*env)->GetStringUTFChars(env, file1, 0);
    xmp.file2 = (*env)->GetStringUTFChars(env, file2, 0);

    xdl_merge(
            &native_orig,
            &native_mf1,
            &native_mf2,
            &xmp,
            &result);

    (*env)->ReleaseStringUTFChars(env, ancestor, xmp.ancestor);
    (*env)->ReleaseStringUTFChars(env, file1, xmp.file1);
    (*env)->ReleaseStringUTFChars(env, file2, xmp.file2);

    jbyteArray ret = (*env)->NewByteArray(result.size);
    (*env)->SetByteArrayRegion(ret, 0, result.size, result.ptr);
    free(result.ptr);
    return ret;
}
