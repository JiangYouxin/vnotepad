LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := xdiff
LOCAL_SRC_FILES := net_jiangyouxin_vnotepad_SyncTask.c xdiffi.c xemit.c xhistogram.c xmerge.c xpatience.c xprepare.c xutils.c

include $(BUILD_SHARED_LIBRARY)

